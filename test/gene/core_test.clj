(ns gene.core-test
  (:use clojure.test
        gene.core))

(deftest evolve-test
  (testing "Evolution"
    (let [problem {:score identity
                   :random-solution #(rand-int 11)
                   :mutate #(mod (inc %) 11)
                   :crossover max
                   :population-size 5
                   :n-generations 5}
        final-solution (first (evolve problem))]
        (is (= final-solution 10)))))
