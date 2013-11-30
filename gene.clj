(defn random-char []
  (let [i (rand-int 27)]
    (if (= i 26)
      \space
      (char (+ i (int \A))))))

(defn random-word [n]
  (clojure.string/join ""
   (take n (repeatedly random-char))))

(defn n-equal-chars [a b]
  (let [zipped_chars (apply map vector (map seq [a b]))
        char_scores (map #(apply = %) zipped_chars)]
    (count (filter identity char_scores))))

;http://en.wikipedia.org/wiki/Crossover_(genetic_algorithm)
(defn one-point-crossover [a b]
  (let [point (inc (rand-int (count a)))]
     (concat
      (take point a)
      (drop point b))))

(defn mutate [x]
  (let [point (rand-int (count x))]
    (clojure.string/join
     (concat
       (take point x)
       [(random-char)]
       (drop (inc point) x)))))

(defn breed [parents]
  (apply one-point-crossover
    (partition 2 (shuffle parents))))

(defn select [score population n]
  (take n (reverse (sort-by score population))))

(defn next-generation [score population]
  (let [most-fit (select score population (/ (count population) 4))]
    (println (first most-fit))
    (concat
      most-fit
      (map mutate most-fit)
      (mapcat (fn [_] breed most-fit) (range 2))
     )))

(def max-generations 200)

(def evolve
  (fn [score population]
    (loop [gen population
           cnt max-generations]
      (if (zero? cnt)
        gen
        (recur (next-generation score gen) (dec cnt))))))


(def target "METHINKS IT IS LIKE A WEASEL")

(def score
  (partial n-equal-chars target))

(def population-size 200)

(def initial-population
  (take population-size
        (repeatedly #(random-word (count target)))))

(count (next-generation score initial-population))


(select score (evolve score initial-population) 1)

