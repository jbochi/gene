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


(deftest best-score-stop-condition
  (testing "Stop condition"
    (let [problem {:score identity
                   :random-solution #(rand-int 11)
                   :mutate #(mod (inc %) 11)
                   :crossover max
                   :population-size 5
                   :n-generations 1000000 ;a huge number, so test would be very slow without a stop condition
                   :best-possible-score 10}
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
                   :listen-addr "inproc://america"}
          solution (future (evolve problem))
          settler 100]
      (future
        (Thread/sleep 100)
        (let [export (emigration "inproc://america")]
          (export (dec settler))
          (Thread/sleep 50)
          (export settler)))
      (is (= settler (first @solution))))))

(deftest evolve-test
  (testing "Emigration"
    (let [exported (immigration "inproc://best")
          problem {:score identity
                   :random-solution (fn [] 42)
                   :mutate identity
                   :crossover max
                   :population-size 5
                   :n-generations 5
                   :send-addr "inproc://best"}
        final-solution (first (evolve problem))]
      (is (= 42 (exported))))))
