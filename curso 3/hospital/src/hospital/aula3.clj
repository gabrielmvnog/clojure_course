(ns hospital.aula3
  (:require [hospital.logic :as h.logic]
            [hospital.model :as h.model])
  (:use [clojure pprint]))

; Símbolo que qualquer thread que acessar essa namespace vai ter
; acesso a ele com o valor padrão Gabriel, o problema é que pode
; ser redefinido. Posso redefinir até por um número
(def nome "Gabriel")

; Redefinindo, por exemplo

(def nome 123)

; No let não fazemos o rebinding do simbolo
; apenas fazemos o shadowing, ou seja, alteramos o valor 
; por um tempo, mas logo que sai do let aninhado ele já volta a ser o
; valor anterior
(let [nome "Gabriel"]
  ; coisa 1
  ; coisa 2
  (println nome)
  (let [nome "Joao"]
    ; coisa 3
    ; coisa 4
    (println nome))
  (println nome))

(defn testa-atomao []
  (let [hospital-nogueira (atom {:espera h.model/fila_vazia})]
    (println hospital-nogueira)
    (pprint hospital-nogueira)

    ; Traz o valor associado ao Atom
    (println (deref hospital-nogueira))
    (pprint @hospital-nogueira)

    ; Não é assim para alterar o valor de um Atom 
    (pprint (assoc @hospital-nogueira :laboratorio1 h.model/fila_vazia))
    (pprint @hospital-nogueira)

    ; Altera o valor do Atom (uma das maneiras)
    (swap! hospital-nogueira assoc :laboratorio1 h.model/fila_vazia)
    (pprint @hospital-nogueira)
    (swap! hospital-nogueira assoc :laboratorio2 h.model/fila_vazia)
    (pprint @hospital-nogueira)

    ; Update tradicional imutavel, com dreferencia. (Não altera o valor)
    (update @hospital-nogueira :laboratorio1 conj "111")

    ; Usando swap
    (swap! hospital-nogueira update :laboratorio2 conj "111")
    (pprint @hospital-nogueira)))


(testa-atomao)

; Força situação de retry
(defn chega-em-malvado! [hospital pessoa]
  (swap! hospital h.logic/chega-em-pausado :espera pessoa)
  (println "Após inserir" pessoa))

(defn simula-um-dia-em-paralelo
  []
  (let [hospital (atom (h.model/novo-hospital))]
    (.start (Thread. (fn [] (chega-em-malvado! hospital "111"))))
    (.start (Thread. (fn [] (chega-em-malvado! hospital "222"))))
    (.start (Thread. (fn [] (chega-em-malvado! hospital "333"))))
    (.start (Thread. (fn [] (chega-em-malvado! hospital "444"))))
    (.start (Thread. (fn [] (chega-em-malvado! hospital "555"))))
    (.start (Thread. (fn [] (chega-em-malvado! hospital"666"))))
    (.start (Thread. (fn [] (Thread/sleep 8000) (pprint hospital))))))

(simula-um-dia-em-paralelo)

(defn chega-sem-malvado! [hospital pessoa]
  (swap! hospital h.logic/chega-em :espera pessoa)
  (println "Após inserir" pessoa))

(defn simula-um-dia-em-paralelo
  []
  (let [hospital (atom (h.model/novo-hospital))]
    (.start (Thread. (fn [] (chega-sem-malvado! hospital "111"))))
    (.start (Thread. (fn [] (chega-sem-malvado! hospital "222"))))
    (.start (Thread. (fn [] (chega-sem-malvado! hospital "333"))))
    (.start (Thread. (fn [] (chega-sem-malvado! hospital "444"))))
    (.start (Thread. (fn [] (chega-sem-malvado! hospital "555"))))
    (.start (Thread. (fn [] (chega-sem-malvado! hospital "666"))))
    (.start (Thread. (fn [] (Thread/sleep 8000) (pprint hospital))))))

(simula-um-dia-em-paralelo)