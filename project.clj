(defproject uplink "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [javax.mail/mail "1.4.4" :exclusions [javax.activation/activation]]]
  :main ^:skip-aot uplink.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
