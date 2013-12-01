(ns uncap.reader
  (:require [clojure.java.io :as io])
  (:use [clojure.string :only [split]]))

(defn read-file [filename]
  (let [rdr (io/reader filename)
        first-line (.readLine rdr)
        n (read-string ((split first-line #"\s") 2))]
    {:n n}))
