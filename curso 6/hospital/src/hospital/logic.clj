(ns hospital.logic
  (:require [hospital.model :as h.model]
            [schema.core :as s]))

(defn cabe-na-fila?
  [hospital departamento]
  (some-> hospital
          departamento
          count
          (< 5)))

;; Função privada
(defn- tenta-colocar-na-fila
  [hospital departamento pessoa]
  (when (cabe-na-fila? hospital departamento)
    (update hospital departamento conj pessoa)))

(defn chega-em
  [hospital departamento pessoa]
  (if-let [novo-hospital (tenta-colocar-na-fila hospital departamento pessoa)]
    {:hospital novo-hospital :resultado :sucesso}
    {:hospital hospital :resultado :impossivel-colocar-pessoa-na-fila}))


(s/defn atende :- h.model/Hospital
  [hospital :- h.model/Hospital,  departamento :- s/Keyword]
  (update hospital departamento pop))

(s/defn proxima
  [hospital :- h.model/Hospital, departamento :- s/Keyword]
  (-> hospital
      departamento
      peek))

(defn mesmo-tamanho? [hospital, outro-hospital, de, para]
  (= (+ (count (get outro-hospital de)) (count (get outro-hospital para)))
     (+ (count (get hospital de)) (count (get hospital para)))))

(s/defn transfere
  [hospital :- h.model/Hospital, de :- s/Keyword, para :- s/Keyword]
        ;; pre e pos são programação voltado a contratos
        ;; não é muito usado
  {
   :pre [(contains? hospital de), (contains? hospital para)] ;; Condições de entrada
   :pos [(mesmo-tamanho? hospital % de para)] ;; Condições de saída
   }
  (let [pessoa (proxima hospital de)]
    (-> hospital
        (atende de)
        (chega-em para pessoa))))

