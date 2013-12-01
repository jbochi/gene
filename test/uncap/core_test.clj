(ns uncap.core-test
  (:use clojure.test
        uncap.core))

(deftest core-test
  (testing "Core"
    (is (= 932615.75 ((solve {:file "test/uncap/data/cap71.txt"}) :cost)))))
