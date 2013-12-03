(ns gene.core
  (:use gene.migration))

(defn- breed [crossover parents]
  (map #(apply crossover %)
    (partition 2 (shuffle parents))))

(defn- sort-by-fitness [score population]
  (sort-by score > population))

(defn- mix-imigrants [imigrants population]
  (dosync
    (let [new-population (concat @imigrants population)]
      (reset! imigrants ())
      new-population)))

(defn- receive-imigrants [listen imigrants]
  (let [in (immigration listen)]
    (future
      (while true
        (let [new-imigrant (in)]
          (swap! imigrants conj new-imigrant))))))

(defn- first-generation [problem]
  (let [{:keys [random-solution population-size score]} problem]
    (->> (repeatedly random-solution)
         (take population-size)
         (sort-by-fitness score))))

(defn- next-generation [population imigrants problem]
  (let [{:keys [population-size score mutate crossover]} problem
        most-fit (take (/ population-size 4) population)
        mutations (map mutate most-fit)
        childs (->> #(breed crossover most-fit)
                    (repeatedly)
                    (take 2)
                    (apply concat))
        next-gen (mix-imigrants imigrants (concat most-fit mutations childs))]
    (sort-by-fitness score next-gen)))

(defn evolve [problem]
  (let [{:keys [n-generations debug listen]} problem
        imigrants (atom ())]
    (if-not (nil? listen)
      (receive-imigrants listen imigrants))
    (loop [gen (first-generation problem)
           cnt 0]
      (if debug (println "generation #" cnt ":" gen))
      (if (>= cnt n-generations)
        gen
        (recur
          (next-generation gen imigrants problem)
          (inc cnt))))))
