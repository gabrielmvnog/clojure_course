(ns hospital.core
  (:use [clojure pprint])
  (:require [hospital.model :as h.model]))

; Fila de espera
; 
; Da fila de espera ela vai para a fila do laboratório
; 
; Laboratório 1
; Laboratório 2
; Laboratório 3
; 
; Vão ser pego pessoas que vão ir para essas filas


(let [meu-hospital (h.model/novo-hospital)]
  (pprint meu-hospital))