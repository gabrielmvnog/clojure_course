(ns curso.aula4)

(def precos [30 700 1000])

; Recuperando valores

(println (precos 0))
(println (get precos 0))
(println (get precos 2))

; Usando get com valor default

(println "valor padrão nil" (get precos 17))
(println "valor padrão 0" (get precos 17 0))
(println "valor padrão 0, mas existe" (get precos 2 0))

; Adicionando valores

(println (conj precos 5)) ; adiciona no final, devolve um novo vetor
(println precos) ; conj não altera o vetor

; Atualizando um valor do vetor

(println (update precos 0 inc))
(println (update precos 0 dec))
(println precos) ; Não altera o vetor original

(defn soma-um
  [valor]
  (inc valor))

(println (update precos 0 soma-um))

; Atualizando todos os valores do vetor

(println "Map:" (map soma-um precos))

(println (range 10))
(println (filter even? (range 10)))

(map soma-um (filter even? (range 10)))

(reduce + precos)

(defn minha-soma
  [valor-1 valor-2]
  (+ valor-1 valor-2))

(reduce minha-soma precos)
(reduce minha-soma (range 10))
(reduce minha-soma [15])
(reduce minha-soma 100000 precos) ; consigo colocar um elemento inicial
(reduce minha-soma 100000 [10])
