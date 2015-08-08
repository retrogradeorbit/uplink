(ns uplink.core
  (:require [clojure.java.io :as io]
            [clojure.string :as string])
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))


(comment

  (def md-cur
    (-> "user.home"
        System/getProperty
        (str "/Maildir")
        io/file
        (io/file "cur")))
  (.lastModified md-cur)
  (-> md-cur .listFiles count)
  (-> md-cur .listFiles (aget 2) .getName (string/split #":"))


  (def mdir (io/file maildir))




  )
