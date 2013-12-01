(ns gene.weasel-test
  (:use clojure.test
        gene.weasel))

(deftest weasel-test
  (testing "Weasel"
    (let [target "METHINKS IT IS LIKE A WEASEL"
          solution (evolve-phrase target)]
        (is (= solution target)))))
