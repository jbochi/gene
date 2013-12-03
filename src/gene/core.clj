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

(defn- receive-imigrants [listen-addr imigrants]
  (let [in (immigration listen-addr)]
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

(defn- better-than-best [individual best score]
  (or (nil? best) (> (score individual) (score best))))

(defn- print-new-best-solution [watch-key best-ref old-state new-state]
  (println "New best solution found:" new-state))

(defn evolve [problem]
  (let [{:keys [n-generations debug score listen-addr]} problem
        imigrants (atom ())
        best (atom nil)]
    (if-not (nil? listen-addr)
      (receive-imigrants listen-addr imigrants))
    (if debug
      (add-watch best :print-best print-new-best-solution))
    (loop [gen (first-generation problem)
           cnt 0]
      (if debug
        (println "generation #" cnt ":" gen))
      (if (better-than-best (first gen) @best score)
        (reset! best (first gen)))
      (if (>= cnt n-generations)
        gen
        (recur
          (next-generation gen imigrants problem)
          (inc cnt))))))
