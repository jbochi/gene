(ns weasel.core-test
  (:use clojure.test
        weasel.core))

(deftest weasel-test
  (testing "Weasel"
    (let [target "METHINKS IT IS LIKE A WEASEL"
          solution (evolve-phrase target)]
        (is (= solution target)))))
