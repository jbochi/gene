(ns uncap.reader-test
  (:use clojure.test
        uncap.reader))

(deftest read-test
  (testing "Reader"
    (let [data (read-file "test/uncap/data/cap71.txt")]
        (is (= 16 (data :m)))
        (is (= 50 (data :n)))
        (is (= 16 (count (data :costs))))
        (is (= 7500.0 (first (data :costs)))))))
