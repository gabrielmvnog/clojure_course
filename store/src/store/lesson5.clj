(ns store.lesson4
  (:require [store.db :as s.db]
            [store.logic :as s.logic]))

; Podemos dizer que o Keep é como se fosse um Map e um Filter juntos, já que podemos 
; alterar valores e também podemos filtra retornando nil

(defn paid-a-lot? [user-info]
  (> (:total-value user-info) 500))


(let [orders (s.db/all-orders)
      summary (s.logic/summary-by-user orders)]
  (println "keep" (keep paid-a-lot? summary))
  (println "filter" (filter paid-a-lot? summary)))



(defn paid-a-lot? [user-info]
  (println "paid-a-lot?" (:user user-info))
  (> (:total-value user-info) 500))


(let [orders (s.db/all-orders)
      summary (s.logic/summary-by-user orders)]
  (println "keep" (keep paid-a-lot? summary))
  (println "filter" (filter paid-a-lot? summary)))

; Tentando entender o que está acontecendo com a order dos prints

(println (range 10))
(println (take 2 (range 1000000000000000000))) ; Not eager


; MAP are using CHUNKS of 32

(defn filter-one [x]
  (println "filter-one" x)
  x)

(defn filter-two [x]
  (println "filter-two" x)
  x)

(println (map filter-two (map filter-one (range 10))))

(->> (range 10)
     (map filter-one)
     (map filter-two))

(->> (range 50)
     (map filter-one)
     (map filter-two))

(->> '(1 2 3 4 5 6 7 8 9 0) ; Totaly lazy
     (map filter-one)
     (map filter-two))


; Forcing map to not proccess chunks

(->> (range 50)
     (mapv filter-one)
     (mapv filter-two))
