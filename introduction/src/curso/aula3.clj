(ns curso.aula3)

; (defn aplica-desconto?
;   [valor-bruto]
;   (> valor-bruto 100))


; (defn valor-descontado
;   [valor-bruto]
;   (if (aplica-desconto? valor-bruto)
;       (let [taxa-de-desconto (/ 10 100) 
;             desconto (* valor-bruto taxa-de-desconto)]
;         (- valor-bruto desconto))
;     valor-bruto))
    
          
; (println (valor-descontado 1000))
; (println (valor-descontado 100))
; (println (aplica-desconto? 1000))
; (println (aplica-desconto? 100))


; (defn aplica-desconto?
;   [valor-bruto]
;   (> valor-bruto 100))


(def aplica-desconto? #(> % 100))

(defn valor-descontado
  [aplica? valor-bruto]
  (if (aplica? valor-bruto)
    (let [taxa-de-desconto (/ 10 100)
          desconto (* valor-bruto taxa-de-desconto)]
      (- valor-bruto desconto))
    valor-bruto))

(println (valor-descontado aplica-desconto? 100))