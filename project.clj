(defproject alekcz/datahike-firebase "0.2.0-SNAPSHOT"
  :description "Datahike with Firebase as data storage"
  :url "https://github.com/alekcz/datahike-firebase"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [ [org.clojure/clojure "1.10.1" :scope "provided"]
                  [environ "1.2.0"]
                  [alekcz/konserve-fire "0.4.0-20210707.170137-1"]
                  [io.replikativ/datahike "0.3.6"]]
  :aot :all                
  :repl-options {:init-ns datahike-firebase.core}
  :plugins [[lein-cloverage "1.2.2"]]
  :profiles { :dev {:dependencies [[metosin/malli "0.4.0"]]}})
