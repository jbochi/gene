(ns uncap.core
    (:use [gene.core :only [evolve]]
          [uncap.reader :only [read-file]]
          uncap.dna))

(let [data (read-file "test/uncap/data/cap71.txt")
      {:keys [cost-matrix warehouse-costs m]} data
      problem {:score #(- (cost cost-matrix warehouse-costs %))
               :random-solution #(random-set m (inc (rand-int m)))
               :mutate #(mutate % m)
               :crossover crossover
               :population-size 20
               :n-generations 20}
    final-generation (evolve problem)
    solution (first final-generation)]
        (println solution)
        (println (cost cost-matrix warehouse-costs solution)))
