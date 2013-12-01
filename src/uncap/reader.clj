(ns uncap.reader
  (:use [clojure.string :only [split trim]]))

(defn- parse-numbers [text]
  (map read-string (split (trim text) #"\s+")))

(defn read-file [filename]
  (let [text (slurp filename)
        numbers (parse-numbers text)
        [m n] (take 2 numbers)
        warehouse-costs (take m (take-nth 2 (drop 3 numbers)))
        customer-numbers (drop (+ (* 2 m) 2) numbers)
        demands (take-nth (inc m) customer-numbers)]
    {:n n
     :m m
     :warehouse-costs warehouse-costs
     :demands demands
     }))
