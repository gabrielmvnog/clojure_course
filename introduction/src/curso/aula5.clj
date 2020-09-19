(ns curso.aula5)

; Coleção MAP

(def estoque {"mochila" 10, "camiseta" 5})
(println estoque)

; Funções do MAP

(println "Temos" (count estoque) "elementos")
(println "Chaves" (keys estoque))
(println "Valores" (vals estoque)) ; não existe ordem

; Keywords
; Exemplo: :mochila

(def "Usando keywords" estoque {:mochila 10
                                :camiseta 5})

(assoc estoque :mochila 2)
(println estoque) ; Não altera o map original

(update estoque :mochila inc)
(println estoque)

(update estoque :mochila #(- % 3))

(dissoc estoque :mochila)

; Aninhando maps

(def pedido {
             :mochila {:quantidade 2, :preco 80}
             :camiseta {:quantidade 3, :preco 40}
             })

(println pedido)

(assoc pedido :chaveiro {:quantidade 1, :preco 10})
(def pedido (assoc pedido :chaveiro {:quantidade 1, :preco 10})) ; Redefini o pedido
(println pedido)

(pedido :mochila) ; se pedido for nil vai dar um NullPointerException
(get pedido :mochila)
(get pedido :cadeira)
(get pedido :cadeira {})
(:mochila pedido)
(:cadeira pedido)
(:cadeira pedido {})

(:quantidade (:mochila pedido))
(:preco (:mochila pedido))

(update-in pedido [:mochila :quantidade] inc)

; Threading first (encadeamento de chamadas)

(-> pedido
    :mochila
    :quantidade)

(-> pedido
    :mochila
    :quantidade
    inc)