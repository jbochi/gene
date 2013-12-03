(ns gene.migration-test
  (:use clojure.test
        gene.migration))

(deftest migration-test
  (testing "String migration"
    (let [i (immigration "inproc://migration")
          e (emigration "inproc://migration")]
      (e "Sailor!")
      (is (= "Sailor!" (i))))))

(deftest migration-test
  (testing "EDN via TCP Migration"
    (let [i (immigration "tcp://*:9999")
          e (emigration "tcp://localhost:9999")]
      (e #{1 2 3 4})
      (is (= #{1 2 3 4} (i))))))
