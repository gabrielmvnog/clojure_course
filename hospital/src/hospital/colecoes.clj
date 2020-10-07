(ns hospital.colecoes
  (:use [clojure pprint]))

; As funções podem alterar dependendo da estrutura de dados

(defn testa-vetor []
  (let [espera [111 222]]
    (println "-> Array")
    (println espera)
    (println "Insere 333" (conj espera 333))
    (println "Insere 444" (conj espera 444)) ; Adicional no final
    (println "Remove 111" (pop espera))))

(testa-vetor)

(println)

(defn testa-lista []
  (let [espera '(111 222)]
    (println "-> Lista")
    (println espera)
    (println "Insere 333" (conj espera 333))
    (println "Insere 444" (conj espera 444)) ; Adicional no começo
    (println "Remove 222" (pop espera))))

(testa-lista)

(println)

(defn testa-conjunto []
  (let [espera #{111 222}]
    (println "-> Conjunto")
    (println espera)
    (println "Insere 333" (conj espera 111)) ; Não insere, por já ter esse valor
    (println "Insere 333" (conj espera 333))
    (println "Insere 444" (conj espera 444)) ; Adicional no começo
    ; (println "Remove 222" (pop espera)) Não funciona em set por não ter ordem
    ))

(testa-conjunto)

(println)

(defn testa-fila []
  (let [espera (conj clojure.lang.PersistentQueue/EMPTY 111 222)]
    (println "-> Fila")
    (println (seq espera)) ; Precisa do set para imprimir
    (println "Insere 333" (seq (conj espera 333)))
    (println "Remove 222" (seq (pop espera))) ; remove o primeiro e devolve o resto
    (println "Pega o 111" (peek espera)) ; pega só o primeiro
    (pprint espera) ; imprime <- -< para indicar que é uma fila
    ))

(testa-fila)