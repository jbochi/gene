(defproject gene "0.1.0-SNAPSHOT"
  :description "Parallel Genetic Algorithm in Clojure"
  :url "https://github.com/jbochi/gene"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/tools.cli "0.2.4"]
                 [com.keminglabs/zmq-async "0.1.0"]
                 [com.taoensso/nippy "2.5.0"]]
  :main uncap.cli)
