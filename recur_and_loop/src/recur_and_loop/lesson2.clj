(ns recur_and_loop.lesson2)

; ["daniela" "guilherme" "carlos" "paulo" "lucia" "ana"]

(defn counting
  [total elements]
  (if (next elements)
    (recur (inc total) (rest elements))
    (inc total)))

(counting 0 ["daniela" "guilherme" "carlos" "paulo" "lucia" "ana"])
(counting 0 []) ; is returning 1

(defn counting
  [total elements]
  (if (first elements)
    (recur (inc total) (rest elements))
    total))

(counting 0 ["daniela" "guilherme" "carlos" "paulo" "lucia" "ana"])
(counting 0 [])

; It's possible to pass diferents numbers of params in a fuction

(defn my-function
  ([param1] (println param1))
  ([param1 param2] (println param1 "&" param2)))

(my-function "test")
(my-function "test" [])


(defn counting
  ([elements] ; One param
   (counting 0 elements))
  ([total elements] ; Two params
   (if (first elements)
     (recur (inc total) (rest elements))
     total)))

(counting ["daniela" "guilherme" "carlos" "paulo" "lucia" "ana"])
(counting [])


; Using loop


(defn counting
  [elements] ; One param
  (loop [total 0
         nexts-elements elements]
    (if (seq nexts-elements)
      (recur (inc total)
             (next nexts-elements))
      total)))

(counting ["daniela" "guilherme" "carlos" "paulo" "lucia" "ana"])
(counting [])