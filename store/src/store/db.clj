(ns store.db)

(def order1 {:user 15
             :items {:mochila {:id :mochila, :quantity 2, :price 80}
                     :camiseta {:id :camiseta, :quantity 3, :price 40}
                     :tenis {:id :tenis, :quantity 2}}})

(def order2 {:user 15
             :items {:mochila {:id :mochila, :quantity 4, :price 160}
                     :tenis {:id :tenis, :quantity 3}}})

(def order3 {:user 16
             :items {:camiseta {:id :camiseta, :quantity 3, :price 40}
                     :tenis {:id :tenis, :quantity 2}}})

(def order4 {:user 16
             :items {:mochila {:id :mochila, :quantity 2, :price 80}
                     :camiseta {:id :camiseta, :quantity 3, :price 40}
                     :tenis {:id :tenis, :quantity 2}}})

(def order5 {:user 17
             :items {:mochila {:id :mochila, :quantity 2, :price 80}
                     :camiseta {:id :camiseta, :quantity 3, :price 40}
                     :tenis {:id :tenis, :quantity 2}}})

(def order6 {:user 18
             :items {:mochila {:id :mochila, :quantity 2, :price 80}
                     :camiseta {:id :camiseta, :quantity 3, :price 40}
                     :tenis {:id :tenis, :quantity 2}}})


(defn all-orders []
  [order1, order2, order3, order4, order5, order6])