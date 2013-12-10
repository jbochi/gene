; http://en.wikipedia.org/wiki/Weasel_program
(ns weasel.core
    (:use [gene.core :only [evolve]]))

(defn- random-char []
  (let [i (rand-int 27)]
    (if (= i 26)
      \space
      (char (+ i (int \A))))))

(defn- random-phrase [n]
  (clojure.string/join ""
   (take n (repeatedly random-char))))

(defn- n-equal-chars [a b]
  (let [zipped_chars (apply map vector (map seq [a b]))
        char_scores (map #(apply = %) zipped_chars)]
    (count (filter identity char_scores))))

(defn- one-point-crossover [a b]
  (let [point (inc (rand-int (count a)))]
     (apply str (concat (take point a)
                        (drop point b)))))

(defn- mutate [x]
  (let [point (rand-int (count x))]
    (clojure.string/join
     (concat
       (take point x)
       [(random-char)]
       (drop (inc point) x)))))

(defn evolve-phrase [target]
  (let [problem {:score (partial n-equal-chars target)
                 :random-solution (partial random-phrase (count target))
                 :crossover one-point-crossover
                 :mutate mutate
                 :population-size 40
                 :n-generations 400}]
      (first (evolve problem))))
