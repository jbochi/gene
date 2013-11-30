(ns gene.uncap-test
  (:use clojure.test
        gene.uncap))

(deftest cost-test
  (testing "Cost"
    (is (= 3 (cost [[1 2] [2 1]] #{0})))
    (is (= 2 (cost [[1 2] [2 1]] #{0 1})))
    (is (= 2 (cost [[1 1] [2 2] [3 3]] #{0})))
    (is (= 2 (cost [[1 1] [2 2] [3 3]] #{0 1})))
    (is (= 4 (cost [[1 1] [2 2] [3 3]] #{1})))
    (is (= 6 (cost [[1 1] [2 2] [3 3]] #{2})))))

(deftest crossover-test
  (testing "Crossover"
    (is (= #{1 2 3}  (crossover #{1 2 3} #{1 2 3})))
    (is (#{#{1} #{2}} (crossover #{1} #{2})))))

(deftest mutate-test
  (testing "Mutate"
    (is (#{#{0} #{1}} (mutate #{1} 2)))))

(deftest random-set-test
  (testing "Random"
    (is (= #{0 1 2} (random-set 3 3)))))
