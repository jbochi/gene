(ns gene.evolve)

(defn breed [crossover parents]
  (apply crossover
    (partition 2 (shuffle parents))))

(defn select-n [score population n]
  (take n (reverse (sort-by score population))))

(defn next-generation [score population mutate crossover]
  (let [most-fit (select-n score population (/ (count population) 4))]
    (concat
      most-fit
      (map mutate most-fit)
      (mapcat (fn [_] (partial crossover breed) most-fit) (range 2))
     )))

(def evolve
  (fn [score population max-generations mutate crossover]
    (loop [gen population
           cnt max-generations]
      (if (zero? cnt)
        gen
        (recur (next-generation score gen mutate crossover) (dec cnt))))))
