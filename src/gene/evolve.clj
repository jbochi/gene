(ns gene.evolve)

(defn- breed [crossover parents]
  (apply crossover
    (partition 2 (shuffle parents))))

(defn- sort-by-fitness [score population]
  (reverse (sort-by score population)))

(defn- next-generation [score population mutate crossover]
  (let [most-fit (take (/ (count population) 4) population)]
    (sort-by-fitness
      score
      (concat
        most-fit
        (map mutate most-fit)
        (mapcat (fn [_] (partial crossover breed) most-fit) (range 2))))))

(def evolve
  (fn [score population max-generations mutate crossover]
    (loop [gen (sort-by-fitness score population)
           cnt max-generations]
      (if (zero? cnt)
        gen
        (recur (next-generation score gen mutate crossover) (dec cnt))))))
