(ns gene.core
    (:use gene.data gene.uncap gene.evolve))

(def n 15)
(def p 10)

(defn score [warehouses]
  (- (cost cost-matrix warehouses)))

(def max-generations 20)

(def population-size 20)

(def initial-population
  (take population-size
        (repeatedly #(random-set n p))))

(def final-generation (evolve score initial-population max-generations #(mutate % n) crossover))

(def solution (first final-generation))

(println solution)
(println (score solution))
