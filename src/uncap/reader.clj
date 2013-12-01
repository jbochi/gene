(ns uncap.reader
  (:require [clojure.java.io :as io]))

(defn read-file [filename]
  (let [rdr (io/reader filename)]
    {:n (.readLine rdr)}))
