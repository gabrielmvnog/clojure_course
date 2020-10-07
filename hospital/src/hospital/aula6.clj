(ns hospital.aula4
  (:use [clojure pprint])
  (:require [hospital.model :as h.model]))

; Aula isolada

; O retry acontece sempre que muda algo no Atom, mas isso nem sempre faz sentindo.
; O que pode ser feito é criar vários Atoms, quando não é necessário o lock ou um retry.
; 
; Atom não dá suporte para transação de dois atoms, o que pode ser feito é utilizar o ref nesse caso

(defn cabe-na-fila? [fila]
  (-> fila
      count
      (< 5)))

(defn chega-em 
  [fila pessoa]
  (if (cabe-na-fila? fila)
    (conj fila pessoa)
    (throw (ex-info "Fila já está cheia" {:tentando-adicionar pessoa}))))

(defn chega-em! [hospital pessoa]
  "troca de referencia via ref-set"
  (let  [fila (get hospital :espera)] 
    (ref-set fila (chega-em @fila pessoa)))) ; O @ aqui é o mesmo que dref

(defn chega-em! [hospital pessoa]
  "troca de referencia via alter"
  (let  [fila (get hospital :espera)]
    (alter fila chega-em pessoa)))

(defn simula-um-dia []
  (let [hospital {:espera (ref h.model/fila-vazia)
                  :laboratorio1 (ref h.model/fila-vazia)
                  :laboratorio2 (ref h.model/fila-vazia)
                  :laboratorio3 (ref h.model/fila-vazia)}]
    (dosync (chega-em! hospital "Gabriel")
            (chega-em! hospital "Maria")
            (chega-em! hospital "Guilherme")
            (chega-em! hospital "Lucia")
            (chega-em! hospital "Paulo")
            ; (chega-em! hospital "Daniela")
            )
    (pprint hospital)))

; (simula-um-dia)

(defn async-chega-em! [hospital pessoa]
  (future (Thread/sleep (rand 5000))
          (dosync
           (println "Tentando" pessoa)
           (chega-em! hospital pessoa))))

(defn simula-um-dia-async []
  (let [hospital {:espera (ref h.model/fila-vazia)
                  :laboratorio1 (ref h.model/fila-vazia)
                  :laboratorio2 (ref h.model/fila-vazia)
                  :laboratorio3 (ref h.model/fila-vazia)}
        futures (mapv #(async-chega-em! hospital %) (range 10))]
    (future (dotimes [_ 8] (Thread/sleep 1000) (pprint hospital) (pprint futures)))
    ))

(simula-um-dia-async)

(println (future 15))
(println (future ((Thread/sleep 1000) 15)))