(ns hospital.aula1
  (:use clojure.pprint))

; pacientes {15 {paciente 15}, 23 {paciente 23}}
; paciente { :id 15 ... }
(defn adiciona-paciente
  [pacientes paciente]
  (let [id (:id paciente)]
    (if id 
      (assoc pacientes id paciente)
      (throw (ex-info "Paciente não possui id" {:paciente paciente})))))

(defn testa-uso-de-pacientes []
  (let [pacientes {}
        gabriel {:id 15, :nome "Gabriel", :nascimento "28/9/1995"}
        mayara {:nome "Mayara", :nascimento "25/11/1994"}]
    (pprint (adiciona-paciente pacientes gabriel))
    (pprint (adiciona-paciente pacientes mayara))))

;; (testa-uso-de-pacientes)


;; Paciente vai ser uma classe de Java, por clojure rodar em cima da virtual machine de java,
;; no exemplo abaixo não importa a tipagem a ser passada para a classe, os 3 valores ali são Objects,
;; mas existe a possibilidade de forçar ele aceitar apenas um tipo se passar ^Object na frente, sendo o Object 
;; o tipo que se espera que seja obrigatório

(defrecord Paciente [id nome nascimento])
(->Paciente 15 "Gabriel" "28/09/1995")
(Paciente. 15 "Gabriel" "28/09/1995")
(Paciente. "Gabriel" 15 "28/09/1995")
(map->Paciente {:id 15 :nome "Gabriel" :nascimento "28/09/1995"})

(let [gabriel (->Paciente 15 "Gabriel" "28/09/1995")]
  (println (:id gabriel))
  (println (vals gabriel))
  (println (class gabriel))
  (println (record? gabriel))
  (println (.nome gabriel)))

(map->Paciente {:id 15 :nome "Gabriel" :nascimento "28/09/1995" :rg 9999999}) ;; Com Map é permitido construir o Paciente com mais ou menos campos
;; (Paciente. 15 "Gabriel" "28/09/1995" 999999) Nesse caso é obrigatório estar tudo de acordo com o que se espera de Paciente

(pprint (assoc (Paciente. 15 "Gabriel" "28/09/1995") :id 38))
(pprint (class (assoc (Paciente. 15 "Gabriel" "28/09/1995") :id 38)))


(= (Paciente. 15 "Gabriel" "28/09/1995") (Paciente. 15 "Gabriel" "28/09/1995")) ;; true
(= (Paciente. 152 "Gabriel" "28/09/1995") (Paciente. 15 "Gabriel" "28/09/1995")) ;; false

