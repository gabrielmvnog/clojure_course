(ns recur_and_loop.lesson1)

; Collections
; []
; {}
; '()
; #{}

; Collection's function
; map
; reduce
; filter

; And loops and for ?

(def costumers ["daniela" "guilherme" "carlos" "paulo" "lucia" "ana"])
(first costumers)
(rest costumers)
(rest [])
(next costumers)
(next [])
(seq [])
(seq [1 2 3])


; Implementing a MAP from scratch

(defn my-map
  [function collection]
  (let [first-el (first collection)]
    (function first-el)
    (when (next collection)
      (my-map function (rest collection)))))

(my-map println [])
(my-map println [1 2 3])
(my-map println [1 nil 3])
(my-map println [1 false 3])

; (my-map println (range 100000)) - Execution error (StackOverflowError) at recur_and_loop.core/my-map (form-init3199455238426474339.clj:32) .  
; Can't run that, StackOverflow.

; TAIL RECURSION

(defn my-map
  [function collection]
  (let [first-el (first collection)]
    (function first-el)
    (when (next collection)
      (recur function (rest collection)))))

(my-map println (range 6000)) ; Now this line run, because of the recur function

