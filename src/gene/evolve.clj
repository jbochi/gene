(ns gene.evolve
  (:use gene.data gene.uncap))

(def n 15)
(def p 10)

(defn score [warehouses]
  (- (cost cost-matrix warehouses)))

(defn breed [parents]
  (apply crossover
    (partition 2 (shuffle parents))))

(defn select-n [score population n]
  (take n (reverse (sort-by score population))))

(defn next-generation [score population]
  (let [most-fit (select-n score population (/ (count population) 4))]
    (concat
      most-fit
      (map #(mutate % n) most-fit)
      (mapcat (fn [_] breed most-fit) (range 2))
     )))

(def evolve
  (fn [score population max-generations]
    (loop [gen population
           cnt max-generations]
      (if (zero? cnt)
        gen
        (recur (next-generation score gen) (dec cnt))))))

;;;;;;;;;;;;;;;;;

(def max-generations 20)

(def population-size 20)

(def initial-population
  (take population-size
        (repeatedly #(random-set n p))))

(def solution (first (select-n score (evolve score initial-population max-generations) 1)))

(println solution)
(println (score solution))
