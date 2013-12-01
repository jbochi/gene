(ns uncap.reader
  (:require [clojure.java.io :as io])
  (:use [clojure.string :only [split trim]]))

(defn read-file [filename]
  (let [rdr (io/reader filename)
        first-line (.readLine rdr)
        [m n] (map read-string (split (trim first-line) #"\s"))]
    {:n n :m m}))
