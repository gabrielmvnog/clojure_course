(ns hospital.aula2
  (:use clojure.pprint))

;; (defrecord Paciente [id nome nascimento])

;; Paciente Plano de Saude => + plano de saude
;; Paciente Particular => + nada

;; Poderia ser feito um Paciente e os outros herdarem desse paciente, porém isso pode dar problema
;; na orientação objeto, não vale a pena. Como é apenas um campo a mais, vale por na mão mesmo.

(defrecord PacienteParticular [id nome nascimento])
(defrecord PacientePlanoDeSaude [id nome nascimento plano])

;; Regras diferentes para tipos diferentes
;; deve-assinar-pre-autorizacao?
;; Se Particular => valor >= 50
;; Se PlanoDeSaude => procedimento NAO esta no plano


;; (defn deve-assinar-pre-autorizacao? [paciente procedimento valor]
;;   (if (= PacienteParticular (type paciente))
;;     (>= valor 50)
;;     ;; asssumindo que existe os dois tipos
;;     (if (= PacientePlanoDeSaude (type paciente))
;;       (let [plano (get paciente :plano)]
;;         (not (some #(= % procedimento) plano)))
;;       true)))


;; Se assemelha a uma interface em Java
(defprotocol Cobravel
  (deve-assinar-pre-autorizacao? [paciente procedimento valor]))

(extend-type PacienteParticular
  Cobravel
  (deve-assinar-pre-autorizacao? [paciente procedimento valor]
    (>= valor 50)))

(extend-type PacientePlanoDeSaude
  Cobravel
  (deve-assinar-pre-autorizacao? [paciente procedimento valor]
    (let [plano (:plano paciente)] (not (some #(= % procedimento) plano)))))

;; poderia ser feito algo da seguinte maneira

;; (defrecord PacientePlanoDeSaude [id nome nascimento plano]
;;   Cobravel
;;   (deve-assinar-pre-autorizacao? [paciente procedimento valor]
;;     (let [plano (:plano paciente)] (not (some #(= % procedimento) plano)))))

(let [particular (->PacienteParticular 15 "Gabriel" "28/09/1995")
      plano (->PacientePlanoDeSaude  15 "Gabriel" "28/09/1995" [:raio-x, :ultrassom])]
  (pprint (deve-assinar-pre-autorizacao? particular :raio-x 500))
  (pprint (deve-assinar-pre-autorizacao? particular :raio-x 40))
  (pprint (deve-assinar-pre-autorizacao? plano :raio-x 40000)))

;; Polimorfismo

(defprotocol Dateable
  (to-ms [this]))

(extend-type java.lang.Number
  Dateable
  (to-ms [this] this))

(pprint (to-ms 56))

(extend-type java.util.Date
  Dateable
  (to-ms [this] (.getTime this)))

(pprint (to-ms (java.util.Date.)))

(extend-type java.util.Calendar
  Dateable
  (to-ms [this] (to-ms (.getTime this))))

(pprint (to-ms (java.util.GregorianCalendar.)))