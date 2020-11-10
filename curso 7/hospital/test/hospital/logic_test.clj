(ns hospital.logic-test
  (:require [clojure.test :refer :all]
            [hospital.logic :refer :all]
            [hospital.model :as h.model]
            [clojure.test.check.clojure-test :refer (defspec)]
            [clojure.test.check.generators :as gen]
            [schema.core :as s]
            [clojure.test.check.properties :as prop]))

(s/set-fn-validation! true)

(deftest cabe-na-fila?-test
  (testing "Que cabe na fila"
    (is (cabe-na-fila? {:espera []}, :espera)))

  (testing "Que não cabe na fila, a fila está cheia"
    (is (not (cabe-na-fila? {:espera [1 2 3 4 5]} :espera))))

  (testing "Que não cabe na fila, a fila tem mais do que deveria na fila"
    (is (not (cabe-na-fila? {:espera [1 2 3 4 5 6]}, :espera))))

  (testing "Que cabe na fila, com gente na fila"
    (is  (cabe-na-fila? {:espera [1 2 3 4]}, :espera))
    (is (cabe-na-fila? {:espera [1 2]}, :espera)))

  (testing "Que não cabe na fila, quando departamento não existe"
    (is (not  (cabe-na-fila? {:espera [1 2 3 4]}, :raio-x))))

  (testing "Que cabe numa fila uma pessoa com nome aleatório")


  (testing "Que numa fila com uma pessoa aleatória cabe mais gente"
    (doseq [fila  (gen/sample (gen/vector gen/string-alphanumeric 0 4))]

      (is (cabe-na-fila? {:espera fila}, :espera)))))

;; (deftest chega-em-test
;;   (testing  "Que é colocada uma pessoa em filas menores que 5"
;;     (doseq [fila  (gen/sample (gen/vector gen/string-alphanumeric 0 4))
;;             pessoa (gen/sample gen/string-alphanumeric)])))

;; tomar cuidado para ficar rodando o repl nos testes e remover algum caso, pode ser que ele continue
;; rodando, nesse caso, vai ser necessário resetar o repl

(defspec coloca-uma-pessoa-em-filas-menores-que-5 10
  (prop/for-all
   [fila (gen/vector gen/string-alphanumeric 0 4)
    pessoa  gen/string-alphanumeric]
   (is  (=  {:espera (conj fila pessoa)} (chega-em {:espera fila} :espera pessoa)))))

(def nome-aleatorio-gen
  (gen/fmap clojure.string/join (gen/vector gen/char-alphanumeric 5 10)))

(defn transforma-vetor-em-fila [vetor]
  (reduce conj h.model/fila-vazia vetor))

(def fila-nao-cheia-gen
  (gen/fmap transforma-vetor-em-fila (gen/vector nome-aleatorio-gen 0 4)))

(defn transfere-ignorand-erro [hospital de para]
  (try
    (transfere hospital de para)
    (catch clojure.lang.ExceptionInfo e
      hospital)))

(defspec transfere-tem-que-manter-a-quantidade-de-pessoas 5
  (prop/for-all
   [espera fila-nao-cheia-gen
    raio-x fila-nao-cheia-gen
    ultrasom fila-nao-cheia-gen
    vai-para (gen/elements [:raio-x :ultrasom])]

   (let [hospital-inicial {:espera espera, :raio-x raio-x, :ultrasom ultrasom}
         hospital-final (transfere-ignorand-erro hospital-inicial :espera vai-para)]
     (= (total-de-pacientes hospital-inicial)
        (total-de-pacientes hospital-final)))



   true))




