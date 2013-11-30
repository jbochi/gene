(ns gene.core
    (:use [gene.evolve :only [evolve]]
          [gene.data :only [cost-matrix]]
          gene.uncap))

(let [n 15 p 10
      problem {:score #(- (cost cost-matrix %))
               :random-solution (partial random-set n p)
               :mutate #(mutate % n)
               :crossover crossover
               :population-size 20
               :n-generations 20}
    final-generation (evolve problem)
    solution (first final-generation)]
        (println solution)
        (println (cost cost-matrix solution)))
