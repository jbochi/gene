(ns gene.core
    (:use [gene.evolve :only [evolve]]
          [gene.data :only [cost-matrix]]
          gene.uncap))

(let [n 15 p 10
      problem {:score #(- (cost cost-matrix %))
               :random-solution (partial random-set n p)
               :mutate #(mutate % n)
               :population-size 20
               :max-generations 20
               :crossover crossover}
    final-generation (evolve problem)
    solution (first final-generation)]
        (println solution)
        (println (cost cost-matrix solution)))
