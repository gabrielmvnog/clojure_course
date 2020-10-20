(ns store.lesson4
  (:require [store.db :as s.db]
            [store.logic :as s.logic]))

(s.db/all-orders)

(let [summary (s.logic/summary-by-user s.db/all-orders)
      orders (s.db/all-orders)]
  (println "Summary" summary)
  (println "Sorted by value" (sort-by :total-value summary))
  (println "Sorted by value Reversed" (reverse (sort-by :total-value summary)))
  (println "Sorted by id" (sort-by :user summary))
  (println "Sorted by id Reversed" (reverse (sort-by :user summary)))

  (println (get-in orders [0 :items])))

(defn summary-sorted [orders]
  (->> orders
       (sort-by :total-value)))

(let [summary (s.logic/summary-by-user s.db/all-orders)
      orders (summary-sorted summary)]
  (println "Summary" orders)
  (println "nth 1" (nth orders 1))
  (println "get 1" (get orders 1))
  (println "take" (take 2 orders)))

(let [summary (s.logic/summary-by-user s.db/all-orders)
      orders (summary-sorted summary)]
  (println (filter #(> (:total-value %) 50) orders))
  (println (not (empty? (filter #(> (:total-value %) 50) orders))))
  (println (some #(> (:total-value %) 50) orders)))