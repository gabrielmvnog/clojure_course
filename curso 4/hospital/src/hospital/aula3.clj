(ns hospital.aula3
  (:use clojure.pprint)
  (:require [hospital.logic :as h.logic]))

;; Simulando carregar um paciente de um banco

(defn carrega-paciente [id]
  (println "Carregando" id)
  (Thread/sleep 1000)
  {:id id, :carregado-em (h.logic/agora)})


(pprint (carrega-paciente 15))
(pprint (carrega-paciente 30))

;; Criando um cache

(defn carrega-se-nao-existe ;; Função pura
  [cache id carregadora]
  (if (contains? cache id)
    cache
    (let [paciente (carregadora id)]
      (assoc cache id paciente))))


(carrega-se-nao-existe {} 15 carrega-paciente)
(carrega-se-nao-existe {15 {:id 15}} 15 carrega-paciente)

;; Implementando o cache de forma orientada a objeto

(defprotocol Carregavel
  (carrega! [this id]))

(defrecord Cache [cache carregadora]

  Carregavel
  (carrega! [this id]
    (swap! cache carrega-se-nao-existe id carregadora)
    (get @cache id)))


(def pacientes (->Cache (atom {}), carrega-paciente))

(pprint pacientes)
(carrega! pacientes 15)
(carrega! pacientes 30)
(carrega! pacientes 15)
(pprint pacientes)

