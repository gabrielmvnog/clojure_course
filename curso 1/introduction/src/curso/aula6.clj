(ns curso.aula6)

; Funções
; - map
; - filter
; - reduce

(def pedido {:mochila {:quantidade 2, :preco 80}
             :camiseta {:quantidade 3, :preco 40}})

; A função abaixo foi para verificar se funcionava, porém não funcionou.

;; (defn imprime-e-15 [chave valor]
;;   (println "chave" chave "valor" valor)
;;   15)

(defn imprime-e-15 [[chave valor]] ; desestrutura em 2
  (println "chave" chave "valor" valor)
  15)

(println (map imprime-e-15 pedido))

(defn preco-por-produto
  [[_ valor]]
  (* (:quantidade valor) (:preco valor)))

(map preco-por-produto pedido)
(reduce + (map preco-por-produto pedido))

(defn total-do-pedido
  [pedido]
  (reduce + (map preco-por-produto pedido)))

(total-do-pedido pedido)

; Trabalhando com threading

(defn total-do-pedido
  "Usando threading"
  [pedido]
  (->> pedido ; Threading last
      (map preco-por-produto)
      (reduce +)))

(total-do-pedido pedido)



(defn preco-total-do-produto
  [produto]
  (* (:quantidade produto) (:preco produto)))

(defn total-do-pedido
  "Usando threading"
  [pedido]
  (->> pedido ; Threading last
       vals
       (map preco-total-do-produto)
       (reduce +)))

(total-do-pedido pedido)



; Trabalhando com filter no MAP

(def pedido {:mochila {:quantidade 2, :preco 80}
             :camiseta {:quantidade 3, :preco 40}
             :chaveiro {:quantidade 1}})

(defn gratuito?
  [item] 
  (< (:preco item 0) 1))

(filter (fn [[_, item]]  (gratuito? item)) pedido)
(filter #(gratuito? (second %)) pedido)

(defn pago?
  [item]
  (not (gratuito? item)))

(filter #(pago? (second %)) pedido)

(filter #((comp not gratuito?) (second %)) pedido) ; a função pago é um composição de funções not + gratuito, posso usar o comp

(->> pedido 
     (map :quantidade)
     (map count))