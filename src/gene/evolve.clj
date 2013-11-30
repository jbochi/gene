(ns gene.evolve)

(defn- breed [crossover parents]
  (map #(apply crossover %)
    (partition 2 (shuffle parents))))

(defn- sort-by-fitness [score population]
  (sort-by score > population))

(defn- next-generation [score population population-size mutate crossover]
  (let [most-fit (take (/ population-size 4) population)]
    (sort-by-fitness
      score
      (concat
        most-fit
        (map mutate most-fit)
        (mapcat (fn [_] (partial crossover breed) most-fit) (range 2))))))

(def evolve
  (fn [score random-solution population-size max-generations mutate crossover]
    (let [population (->> (repeatedly random-solution)
                          (take population-size)
                          (sort-by-fitness score))]
      (loop [gen population
             cnt max-generations]
        (if (zero? cnt)
          gen
          (recur (next-generation score gen population-size mutate crossover) (dec cnt)))))))
