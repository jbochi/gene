(ns gene.evolve)

(defn- breed [crossover parents]
  (map #(apply crossover %)
    (partition 2 (shuffle parents))))

(defn- sort-by-fitness [score population]
  (sort-by score > population))

(defn- next-generation [population problem]
  (let [{:keys [population-size score mutate crossover]} problem
        most-fit (take (/ population-size 4) population)]
    (sort-by-fitness score
      (concat
        most-fit
        (map mutate most-fit)
        (->> #(breed crossover most-fit)
             (repeatedly)
             (take 2)
             (apply concat))))))

(defn evolve [problem]
  (let [{:keys [random-solution population-size score n-generations debug]} problem
        population (->> (repeatedly random-solution)
                        (take population-size)
                        (sort-by-fitness score))]
    (loop [gen population
           cnt n-generations]
      (if debug (println cnt (first gen)))
      (if (zero? cnt)
        gen
        (recur (next-generation gen problem) (dec cnt))))))
