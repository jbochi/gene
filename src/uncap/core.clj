(ns uncap.core
    (:use [gene.core :only [evolve]]
          [uncap.reader :only [read-file]]
          uncap.dna))

(defn solve [opts]
  (let [data (read-file (opts :file))
        {:keys [cost-matrix warehouse-costs m]} data
        problem {:score #(- (cost cost-matrix warehouse-costs %))
                 :random-solution #(random-set m (inc (rand-int m)))
                 :mutate #(mutate % m)
                 :crossover crossover
                 :population-size 20
                 :n-generations 40}
      final-generation (evolve (merge problem opts))
      solution (first final-generation)]
        {:solution solution
         :cost (cost cost-matrix warehouse-costs solution)}))
