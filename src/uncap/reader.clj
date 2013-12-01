(ns uncap.reader
  (:require [clojure.java.io :as io])
  (:use [clojure.string :only [split trim]]))

(defn- read-numbers [rdr]
  (->> (.readLine rdr)
       (trim)
       (#(split % #"\s"))
       (map read-string)))

(defn- read-costs [rdr n]
  (for [_ (range n)]
    (second (read-numbers rdr))))

(defn read-file [filename]
  (let [rdr (io/reader filename)
        [m n] (read-numbers rdr)]
    {:n n
     :m m
     :costs (read-costs rdr m)}))
