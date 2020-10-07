(ns hospital.aula1
  (:use [clojure pprint])
  (:require [hospital.model :as h.model]
            [hospital.logic :as h.logic]))

; Aqui é utilizado o hospital com ROOT BIND, o que não é recomendado,
; porém foi realizado apenas para visualizar o que está acontecendo, e porque
; não foi ensinado outra maneira de re-atribuir valores em uma variável ainda.
; 
; GLOBAL não é uma boa prática.
; 

(defn simula-um-dia []
  (def hospital (h.model/novo-hospital))
  (def hospital (h.logic/chega-em hospital :espera "111"))
  (def hospital (h.logic/chega-em hospital :espera "222"))
  (def hospital (h.logic/chega-em hospital :espera "333"))

  (pprint hospital)

  (def hospital (h.logic/chega-em hospital :laboratorio1 "444"))
  (def hospital (h.logic/chega-em hospital :laboratorio3 "555"))

  (pprint hospital)

  (def hospital (h.logic/atende hospital :laboratorio1))
  (def hospital (h.logic/atende hospital :espera))

  (pprint hospital)

  (def hospital (h.logic/chega-em hospital :espera "666"))
  (def hospital (h.logic/chega-em hospital :espera "777"))
  (def hospital (h.logic/chega-em hospital :espera "888"))

  (pprint hospital)

;   (def hospital (h.logic/chega-em hospital :espera "999")) Vai sempre ter erro

  (pprint hospital))


(simula-um-dia)


(defn chega-em-malvado [pessoa]
  (def hospital (h.logic/chega-em-pausado hospital :espera pessoa))
  (println "Após inserir" pessoa))


(defn simula-um-dia-em-paralelo
  []
  (def hospital (h.model/novo-hospital))
  (.start (Thread. (fn [] (chega-em-malvado "111"))))
  (.start (Thread. (fn [] (chega-em-malvado "222"))))
  (.start (Thread. (fn [] (chega-em-malvado "333"))))
  (.start (Thread. (fn [] (chega-em-malvado "444"))))
  (.start (Thread. (fn [] (chega-em-malvado "555"))))
  (.start (Thread. (fn [] (chega-em-malvado "666"))))
  (.start (Thread. (fn [] (Thread/sleep 4000) (pprint hospital)))))

(simula-um-dia-em-paralelo)