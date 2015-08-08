(ns uplink.core
  (:require [clojure.java.io :as io]
            [clojure.java.shell :refer [sh]]
            [clojure.string :as string])
  (:import [javax.mail Session]
           [javax.mail.internet MimeMessage]
           [java.util Properties])
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(def invalid-username-regexp #"[^A-Za-z0-9\-\._]+")

(defn get-users-homedir
  "return the home directory for the passed in user. Invalid users,
  returns nil. If the shell call fails (it will on Windows), returns
  nil"
  [username]
  (when-not
      (re-find invalid-username-regexp username)
    (let [{:keys [out exit]}
          (->> username
               (str "echo ~")
               (sh "sh" "-c"))]
      (when (and
             (zero? exit)
             (not= "~" (subs out 0 1)))
        (string/trim out)))))

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
  (def message (slurp (-> md-cur .listFiles (aget 10))))
  (def mime-msg (MimeMessage.
                 (Session/getDefaultInstance (Properties.))
                 (io/input-stream (.getBytes message))))

  ;; headers
  (-> mime-msg .getAllHeaderLines enumeration-seq)

  (-> mime-msg .getAllRecipients)
  (def from (-> mime-msg .getFrom (aget 0)))
  (-> mime-msg .getReplyTo)
  (-> mime-msg .getSender)
  (-> mime-msg .getSize)

  ;; subject returns string
  (-> mime-msg .getSubject)

  (-> mime-msg .getLineCount)
  (-> mime-msg .getMessageID)

  ;; the from address details
  (-> from .getType)
  (-> from .toString)

  ;; body
  (-> mime-msg .getContentType)
  (-> mime-msg .getInputStream)

  (def content (-> mime-msg .getContent))

  ;; number of body parts
  (def num (-> content .getCount))

  (map #(-> content (.getBodyPart %) .getContent)
       (range num))



)
