(ns uncap.core
    (:use [gene.core :only [evolve]]
          [uncap.reader :only [read-file]]
          uncap.dna))

(let [data (read-file "test/uncap/data/cap71.txt")
      {:keys [cost-matrix m]} data
      p 10
      problem {:score #(- (cost cost-matrix %))
               :random-solution (partial random-set m p)
               :mutate #(mutate % m)
               :crossover crossover
               :population-size 20
               :n-generations 20}
    final-generation (evolve problem)
    solution (first final-generation)]
        (println solution)
        (println (cost cost-matrix solution)))
