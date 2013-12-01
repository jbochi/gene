(ns uncap.reader
  (:require [clojure.java.io :as io])
  (:use [clojure.string :only [split trim]]))

(defn- read-numbers [rdr]
  (map read-string (split (trim (.readLine rdr)) #"\s")))

(defn read-file [filename]
  (let [rdr (io/reader filename)
        [m n] (read-numbers rdr)]
    {:n n
     :m m}))
