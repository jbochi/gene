(ns uncap.reader-test
  (:use clojure.test
        uncap.reader))

(deftest read-test
  (testing "Reader"
    (let [data (read-file "test/uncap/data/cap71.txt")]
        (is (= 50 (data :n))))))
