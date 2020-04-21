(defproject alekcz/datahike-firebase "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [ [org.clojure/clojure "1.10.0"]
                  [alekcz/konserve-fire "0.0.9-SNAPSHOT"]
                  [io.replikativ/datahike "0.2.1"]
                  [io.replikativ/datahike-postgres "0.1.0"]]
  :repl-options {:init-ns datahike-firebase.core}
  :plugins [[lein-cloverage "1.1.2"]]
  :profiles { :dev {:dependencies [[metosin/malli "0.0.1-SNAPSHOT"]]}})
