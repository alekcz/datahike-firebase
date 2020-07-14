(defproject alekcz/datahike-firebase "0.2.0-SNAPSHOT"
  :description "Datahike with Firebase as data storage"
  :url "https://github.com/alekcz/datahike-firebase"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [ [org.clojure/clojure "1.10.0"]
                  [alekcz/konserve-fire "0.3.0-20200714.163253-5"]
                  [io.replikativ/datahike "0.3.2-20200713.140023-3"]]
  :aot :all                
  :repl-options {:init-ns datahike-firebase.core}
  :plugins [[lein-cloverage "1.1.2"]]
  :profiles { :dev {:dependencies [[metosin/malli "0.0.1-20200404.091302-14"]]}})
