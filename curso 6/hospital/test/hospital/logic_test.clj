(ns hospital.logic-test
  (:require [clojure.test :refer :all]
            [hospital.logic :refer :all]
            [hospital.model :as h.model]
            [schema.core :as s]))

(s/set-fn-validation! true)

;; Teste de Boundery - Testar além das bordas

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
    (is (not  (cabe-na-fila? {:espera [1 2 3 4]}, :raio-x)))))


(deftest chega-em-test

  (let [hospital-cheio {:espera [1 54 23 65 12]}]

    (testing "aceita pessoas enquanto cabe pessoas na fila"
      (is (= {:hospital {:espera [1 2 3 4 5]} :resultado :sucesso}
             (chega-em {:espera [1 2 3 4]}, :espera, 5)))
      (is (= {:hospital  {:espera [1 2 5]} :resultado :sucesso}
             (chega-em {:espera [1 2]}, :espera, 5))))

  ;; Outra maneira de testar
  ;; (is (try (chega-em {:espera [1 23 54 65 13]} :espera 43)
  ;;          false
  ;;          (catch clojure.lang.ExceptionInfo e
  ;;            (= :impossivel-colocar-pessoa-na-fila (:tipo (ex-data e))))))

    (testing "Não aceita pessoas, não cabe mais pessoas na fila"
      (is (= {:hospital hospital-cheio :resultado :impossivel-colocar-pessoa-na-fila}
             (chega-em hospital-cheio :espera 76))))))

(deftest transfere-test
  ;; (testing "aceita pessoas se cabe"
  ;;   (let [hospital-original {:espera (conj h.model/fila-vazia 5) :raio-x h.model/fila-vazia}]
  ;;     (is (= {:hospital {:espera h.model/fila-vazia
  ;;                        :raio-x [5]}
  ;;             :resultado :sucesso}
  ;;            (transfere hospital-original :espera :raio-x))))

  ;;   (let [hospital-original {:espera (conj h.model/fila-vazia 51 5) :raio-x (conj h.model/fila-vazia 13)}]
  ;;     (is (= {:hospital {:espera [5]
  ;;                        :raio-x [13 51]}
  ;;             :resultado :sucesso}
  ;;            (transfere hospital-original :espera :raio-x)))))


  ;; (testing "recusa pessoas se não cabe"
  ;;   (let [hospital-cheio {:espera (conj h.model/fila-vazia 5), :raio-x  (conj h.model/fila-vazia 1 4 52 76 99)}]

  ;;     (is (= {:hospital {:espera []
  ;;                        :raio-x [1 4 52 76 99]}
  ;;             :resultado :impossivel-colocar-pessoa-na-fila}
  ;;            (transfere hospital-cheio :espera :raio-x)))))

  (testing "Não pode invocar transfere sem hospital"
    (is (thrown? clojure.lang.ExceptionInfo (transfere nil :espera :raio-x))))

  (testing "condições obrigatórias"
    (let [hospital {:espera (conj h.model/fila-vazia "5"), :raio-x  (conj h.model/fila-vazia "1" "32")}]
      (is (thrown? AssertionError
                   (transfere hospital :nao-existe :raio-x)))
      (is (thrown? AssertionError
                   (transfere hospital :raio-x :nao-existe))))))