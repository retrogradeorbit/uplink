(ns uplink.core
  (:require [clojure.java.io :as io]
            [clojure.string :as string])
  (:import [javax.mail Session]
           [javax.mail.internet MimeMessage]
           [java.util Properties])
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))


(comment

  (def maildir-filename-separator #":")

  (def md-cur
    (-> "user.home"
        System/getProperty
        (str "/Maildir")
        io/file
        (io/file "cur")))
  (.lastModified md-cur)
  (-> md-cur .listFiles count)
  (map
   #(-> md-cur .listFiles (aget %) .getName (string/split maildir-filename-separator))
   (range 30))
  (println (slurp (-> md-cur .listFiles (aget 10))))

  ;; parse the mail
  (def message (slurp (-> md-cur .listFiles (aget 100))))
  (def mime-msg (MimeMessage.
                 (Session/getDefaultInstance (Properties.))
                 (io/input-stream (.getBytes message))))

  (def mdir (io/file maildir))




  )
