(ns uncap.cli
  (:use [clojure.tools.cli :only [cli]]
        [uncap.core :only [solve]])
  (:gen-class :main true))

(defn run
  [opts args]
  (time (println (solve opts))))

(defn -main [& args]
  (let [[opts args banner]
        (cli args
             ["-f" "--file" "input file path"]
             ["-d" "--debug" "turn debug on" :flag true]
             ["-n" "--n-generations" "number of generations" :default 50 :parse-fn #(Integer. %)]
             ["-p" "--population-size" "population size" :default 20 :parse-fn #(Integer. %)]
             ["-l" "--listen-addr" "address to accept immigrants"]
             ["-s" "--send-addr" "island address where best individuals will migrate to"]
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