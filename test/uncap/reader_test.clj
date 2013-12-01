(ns uncap.reader-test
  (:use clojure.test
        uncap.reader))

(deftest read-test
  (testing "Reader"
    (let [data (read-file "test/uncap/data/cap71.txt")]
        (is (= 16 (data :m)))
        (is (= 50 (data :n)))
        (is (= 16 (count (data :warehouse-costs))))
        (is (= 7500.0 (first (data :warehouse-costs))))
        (is (= 50 (count (data :demands))))
        (is (= 146 (first (data :demands))))
        (is (= 222 (last (data :demands)))))))
