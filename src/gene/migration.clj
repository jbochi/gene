(ns gene.migration
  (:require [com.keminglabs.zmq-async.core :refer [register-socket!]]
            [clojure.core.async :refer [>! <! <!! >!! go chan sliding-buffer]]
            [taoensso.nippy :as nippy]))

(defn queue-channels []
    (repeatedly 2 #(chan (sliding-buffer 64))))

(defn immigration [addr]
  (let [[s-in s-out] (queue-channels)]
    (register-socket! {:in s-in :out s-out :socket-type :rep
                       :configurator (fn [socket] (.bind socket addr))})
    (fn []
      (let [immigrant (nippy/thaw (<!! s-out))]
        (>!! s-in "OK")
        immigrant))))

(defn emigration [addr]
  (let [[s-in s-out] (queue-channels)]
    (register-socket! {:in s-in :out s-out :socket-type :req
                       :configurator (fn [socket] (.connect socket addr))})
    (fn [emigrant]
      (>!! s-in (nippy/freeze emigrant))
      (assert (= "OK" (String. (<!! s-out)))))))
