(ns gene.uncap)

(defn cost
  "Return cost for a given set of warehouses"
  [cost-matrix warehouses]
  (->> warehouses
       (map cost-matrix)
       (apply map vector)
       (map #(reduce min %))
       (reduce +)))

(defn crossover
  "Returns a child of two warehouse sets"
  [wh1 wh2]
  (->> (apply conj wh1 wh2)
       (seq)
       (shuffle)
       (set)
       (take (count wh1))
       (set)))

(defn mutate
  "Returns a new warehouse set based on input"
  [warehouse n]
  (let [in (rand-int n)
        out (rand-nth (seq warehouse))]
    (conj (disj warehouse out) in)))

(defn random-set
  "Returns a random set of p warehouse of n possible warehouses"
  [n p]
  (->> (range n)
       (shuffle)
       (take p)
       (set)))
