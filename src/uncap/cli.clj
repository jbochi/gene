(ns uncap.cli
  (:use [clojure.tools.cli :only [cli]]
        [uncap.core :only [solve]])
  (:gen-class :main true))

(defn run
  [opts args]
  (println (solve (opts :file))))

(defn -main [& args]
  (let [[opts args banner]
        (cli args
             ["-f" "--file" "input file path"]
             )]
    (when (:help opts)
      (println banner)
      (System/exit 0))
    (if
        (and
         (:file opts))
      (do
        (println "")
        (run opts args))
      (println banner))))