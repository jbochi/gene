(ns uncap.cli
  (:use [clojure.tools.cli :only [cli]]
        [uncap.core :only [solve]])
  (:gen-class :main true))

(defn run
  [opts args]
  (let [address (format "tcp://127.0.0.1:%d" (+ 30000 (rand-int 35000)))
        master (future (solve (merge opts {:listen-addr address})))
        worker1 (future (Thread/sleep 50) (solve (merge opts {:send-addr address})))
        worker2 (future (Thread/sleep 50) (solve (merge opts {:send-addr address})))
        worker3 (future (Thread/sleep 50) (solve (merge opts {:send-addr address})))]
    (time (println @master))
    (System/exit 0)))

(defn -main [& args]
  (let [[opts args banner]
        (cli args
             ["-f" "--file" "input file path"]
             ["-d" "--debug" "turn debug on" :flag true]
             ["-g" "--good-enough-score" "If score is reached, simulation is stopped" :parse-fn #(- (read-string %))]
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