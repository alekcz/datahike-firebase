(defproject alekcz/datahike-firebase "0.4.1490"
  :description "Datahike with Firebase as data storage"
  :url "https://github.com/alekcz/datahike-firebase"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [ [org.clojure/clojure "1.10.3"]
                  [org.clojure/core.async "1.5.648"]
                  [environ "1.2.0"]
                  [alekcz/konserve-fire "0.4.0"]
                  [io.replikativ/datahike "0.4.1490"]]
  :aot :all     
  :main datahike-firebase.core            
  :repl-options {:init-ns datahike-firebase.core}
  :plugins [[lein-cloverage "1.2.3"]])
