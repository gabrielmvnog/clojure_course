(ns hospital.aula4
  (:use [clojure pprint])
  (:require [hospital.logic :as h.logic]
            [hospital.model :as h.model]))

(defn chega-em! [hospital pessoa]
  (swap! hospital h.logic/chega-em :espera pessoa))

(defn transfere! [hospital de para]
  (swap! hospital h.logic/transfere de para))

; O atom pode ser usado como uma transação,
; em casos que utilizamos ele com thread, se em algum ponto ele sofrer alguma
; alteração e outra thread estiver utilizando o valor ao mesmo tempo, ele vai dar um retry,
; ou seja, começar do zero

(defn simula-um-dia []
  (let [hospital (atom (h.model/novo-hospital))]
    (chega-em! hospital "joão")
    (chega-em! hospital "maria")
    (chega-em! hospital "daniela")
    (chega-em! hospital "guilherme")
    (transfere! hospital :espera :laboratorio1)
    (transfere! hospital :espera :laboratorio2)
    (transfere! hospital :espera :laboratorio2)
    (transfere! hospital :laboratorio2 :laboratorio3)
    (pprint hospital)))

(simula-um-dia)

(defn atende-completo
  [hospital departamento]
  {:paciente (update hospital departamento peek)
   :hospital (update hospital departamento pop)})

(atende-completo (h.model/novo-hospital) :espera)

(defn atende-completo-que-chama-ambos
  [hospital departamento]
  (let [fila (get hospital departamento)
        peek-pop (juxt peek pop)
        [pessoa fila-atualizada] (peek-pop fila)
        hospital-atualizado (update hospital assoc departamento fila-atualizada)]
    {:paciente pessoa
     :hospital hospital-atualizado}))

(atende-completo-que-chama-ambos (h.model/novo-hospital) :espera)
