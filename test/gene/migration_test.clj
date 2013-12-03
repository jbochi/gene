(ns gene.migration-test
  (:use clojure.test
        gene.migration))

(deftest migration-test
  (testing "Migration"
    (let [i (immigration "inproc://migration")
          e (emigration "inproc://migration")]
      (e "Sailor!")
      (is (= "Sailor!" (i))))))
