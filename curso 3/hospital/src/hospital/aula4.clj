(ns hospital.aula4
  (:require [hospital.logic :as h.logic]
            [hospital.model :as h.model])
  (:use [clojure pprint]))

; Força situação de retry
(defn chega-em-malvado! [hospital pessoa]
  (swap! hospital h.logic/chega-em-pausado :espera pessoa)
  (println "Após inserir" pessoa))

(defn simula-um-dia-em-paralelo-com-mapv
  "Simulação usando um mapv"
  []
  (let [hospital (atom (h.model/novo-hospital))
        pessoas ["111", "222", "333", "444", "555", "666"]]
    
    (mapv #(.start (Thread. (fn [] (chega-em-malvado! hospital %)))) pessoas)
    
    (.start (Thread. (fn [] (Thread/sleep 8000) (pprint hospital))))))

;; (simula-um-dia-em-paralelo-com-mapv)

(defn starta-thread-de-chegada
  (  [hospital]
(fn [pessoa] (starta-thread-de-chegada hospital pessoa)))
  ([hospital pessoa]
   (.start (Thread. (fn [] (chega-em-malvado! hospital pessoa))))))

;; (defn preparadinha
;;   [hospital]
;;   (fn [pessoa] (starta-thread-de-chegada hospital pessoa)))

(defn simula-um-dia-em-paralelo-mapv-extraida
  []
  (let [hospital (atom (h.model/novo-hospital))
        pessoas ["111", "222", "333", "444", "555", "666"]
        starta (starta-thread-de-chegada hospital)]

    (mapv starta pessoas)

    (.start (Thread. (fn [] (Thread/sleep 8000) (pprint hospital))))))

;; (simula-um-dia-em-paralelo-mapv-extraida)

(defn starta-thread-de-chegada
 [hospital pessoa]
  (.start (Thread. (fn [] (chega-em-malvado! hospital pessoa)))))

;; (defn preparadinha
;;   [hospital]
;;   (fn [pessoa] (starta-thread-de-chegada hospital pessoa)))

(defn simula-um-dia-em-paralelo-mapv-com-partial
  []
  (let [hospital (atom (h.model/novo-hospital))
        pessoas ["111", "222", "333", "444", "555", "666"]
        starta (partial starta-thread-de-chegada hospital)]

    (mapv starta pessoas)

    (.start (Thread. (fn [] (Thread/sleep 8000) (pprint hospital))))))

;; (simula-um-dia-em-paralelo-mapv-com-partial)


(defn simula-um-dia-em-paralelo-doseq
  "Estou preocupado em executar para os elementos da minha sequencia"
  []
  (let [hospital (atom (h.model/novo-hospital))
        pessoas ["111", "222", "333", "444", "555", "666"]]

    (doseq [pessoa pessoas] 
      (starta-thread-de-chegada hospital pessoa))

    (.start (Thread. (fn [] (Thread/sleep 8000) (pprint hospital))))))

;; (simula-um-dia-em-paralelo-doseq)

(defn simula-um-dia-em-paralelo-dotimes
  "Estou preocupado em executar N vezes"
  []
  (let [hospital (atom (h.model/novo-hospital))
        pessoas ["111", "222", "333", "444", "555", "666"]]

    (dotimes [pessoa pessoas]
      (starta-thread-de-chegada hospital pessoa))

    (.start (Thread. (fn [] (Thread/sleep 8000) (pprint hospital))))))

(simula-um-dia-em-paralelo-dotimes)