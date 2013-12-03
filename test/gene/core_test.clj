(ns gene.core-test
  (:use clojure.test
        gene.core
        gene.migration))

(deftest evolve-test
  (testing "Evolution"
    (let [problem {:score identity
                   :random-solution #(rand-int 11)
                   :mutate #(mod (inc %) 11)
                   :crossover max
                   :population-size 5
                   :n-generations 5}
        final-solution (first (evolve problem))]
        (is (= 10 final-solution)))))

(deftest evolve-test
  (testing "Colonization"
    (let [problem {:score identity
                   :random-solution #(rand-int 11)
                   :mutate #(mod (inc %) 11)
                   :crossover max
                   :population-size 5
                   :n-generations 10000
                   :listen "inproc://america"}
          solution (future (evolve problem))
          settler 100]
      (future (Thread/sleep 100) ((emigration "inproc://america") settler))
      (is (= settler (first @solution))))))
