(ns uncap.reader
  (:use [clojure.string :only [split trim]]))

(defn- read-number [number]
  (if (= number ".00000")
    0
    (read-string number)))

(defn- parse-numbers [text]
  (map read-number (split (trim text) #"\s+")))

(defn read-file [filename]
  (let [text (slurp filename)
        numbers (parse-numbers text)
        [m n] (take 2 numbers)
        warehouse-costs (->> numbers (drop 3) (take-nth 2) (take m))
        customer-numbers (->> numbers (drop 2) (drop (* m 2)))
        demands (take-nth (inc m) customer-numbers)
        cost-matrix (partition m (inc m) (drop 1 customer-numbers))]
    {:n n
     :m m
     :warehouse-costs (apply vector warehouse-costs)
     :demands (apply vector demands)
     :cost-matrix (apply mapv vector cost-matrix)}))
