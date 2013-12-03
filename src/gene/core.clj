(ns gene.core
  (:use gene.migration))

(defn- breed [crossover parents]
  (map #(apply crossover %)
    (partition 2 (shuffle parents))))

(defn- sort-by-fitness [score population]
  (sort-by score > population))

(defn- next-generation [population problem]
  (let [{:keys [population-size score mutate crossover listen]} problem
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
  (let [{:keys [random-solution population-size score n-generations debug listen]} problem
        population (->> (repeatedly random-solution)
                        (take population-size)
                        (sort-by-fitness score))
        imigrants (atom ())]
    (if-not (nil? listen)
      (let [in (immigration listen)
            new (in)]
        (Thread/sleep 200) ;FIXME
        (swap! imigrants conj new)))
    (loop [gen (sort-by-fitness score (concat population @imigrants))
           cnt 0]
      (if debug (println "generation #" cnt ":" gen))
      (if (>= cnt n-generations)
        gen
        (recur (next-generation gen problem) (inc cnt))))))
