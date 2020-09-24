(ns store.lesson3
  (:require [store.db :as s.db]))

(println (s.db/all-orders))

; Grouping orders

(group-by :user (s.db/all-orders))

(defn my-group-by-func
  [element]
  (println "Element" element)
  (:user element))

(group-by my-group-by-func (s.db/all-orders))

(map count (vals (group-by my-group-by-func (s.db/all-orders))))

(->> (s.db/all-orders)
     (group-by :user)
     vals
     (map count))


(defn sum-total
  [item]
  (let [
        quantity (:quantity (val item))
        value (:price (val item) 0)]
        
    (* quantity value)
   ))
  

(defn process-items
  [items]
  (reduce + (map sum-total (:items items))))


(defn count-total-by-user
  [[user orders]]
  {:user user 
   :total-orders (count orders)
   :total-value (reduce +(map process-items orders))})
   

(->> (s.db/all-orders)
     (group-by :user)
     (map count-total-by-user))
