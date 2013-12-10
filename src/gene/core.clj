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

(defn- receive-imigrants [listen-addr imigrants debug]
  (let [in (immigration listen-addr)]
    (future
      (while true
        (let [new-imigrant (in)]
          (if debug (println "Received imigrant:" new-imigrant))
          (swap! imigrants conj new-imigrant))))))

(defn- send-best [best send-addr debug]
  (let [out (emigration send-addr)]
    (add-watch best :send-best
      (fn [watch-key best-ref old-solution new-solution]
        (if debug (println "Sending emigrant:" new-solution))
        (out new-solution)))))

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

(defn- print-new-best-solution [watch-key best-ref old-solution new-solution]
  (println "New best solution found:" new-solution))

(defn evolve [problem]
  (let [{:keys [n-generations debug score listen-addr send-addr best-possible-score]} problem
        imigrants (atom ())
        best (atom nil)]
    (if listen-addr
      (receive-imigrants listen-addr imigrants debug))
    (if debug
      (add-watch best :print-best print-new-best-solution))
    (if send-addr
      (send-best best send-addr debug))
    (loop [gen (first-generation problem)
           cnt 0]
      (if debug
        (println "generation #" cnt ":" gen))
      (if (better-than-best (first gen) @best score)
        (reset! best (first gen)))
      (if (or (>= cnt n-generations)
              (= (score @best) best-possible-score))
        gen
        (recur
          (next-generation gen imigrants problem)
          (inc cnt))))))
