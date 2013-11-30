(ns gene.uncap)

(defn cost
  "Return cost for a given set of warehouses"
  [cost-matrix warehouses]
  (reduce +
    (map #(reduce min %)
      (apply map vector
        (map cost-matrix warehouses)))))

(defn crossover
  "Returns a child of two warehouse sets"
  [wh1 wh2]
  (set (take (count wh1)
             (shuffle (seq (apply conj wh1 wh2))))))

(defn mutate
  "Returns a new warehouse set based on input"
  [warehouse n]
  (let [in (rand-int n)
        out (rand-nth (seq warehouse))]
    (conj (disj warehouse out) in)))

(defn random-set
  "Returns a random set of p warehouse of n possible warehouses"
  [n p]
  (set (take p (shuffle (range n)))))