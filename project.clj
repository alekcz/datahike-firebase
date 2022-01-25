(defproject alekcz/datahike-firebase "0.2.0-SNAPSHOT"
  :description "Datahike with Firebase as data storage"
  :url "https://github.com/alekcz/datahike-firebase"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [ [org.clojure/clojure "1.10.3" :scope "provided"]
                  [environ "1.2.0"]
                  [io.replikativ/datahike "0.4.1480"
                    :exclusions [org.clojure/clojurescript]]
                  [alekcz/konserve-fire "0.4.0-20210707.210142-2" :exclusions [io.replikativ/incognito]]
                  [com.taoensso/timbre "5.1.2"]]
  :aot :all                
  :repl-options {:init-ns datahike-firebase.core}
  :main datahike-firebase.graal
  :plugins [[lein-cloverage "1.2.2"]
            [lein-shell "0.5.0"]]
  :profiles { :dev {:dependencies [[metosin/malli "0.4.0"]]}}
  :aliases
  {"native"
   ["shell"
    "native-image" 
    "--report-unsupported-elements-at-runtime" 
    "--no-server"
    "--allow-incomplete-classpath"
    "--trace-object-instantiation=java.lang.Thread"
    "--initialize-at-build-time"
    "--no-fallback"
    "--enable-url-protocols=http,https"
    "-jar" "./target/${:uberjar-name:-${:name}-${:version}-standalone.jar}"
    "-H:Name=./target/${:name}"]

   "run-native" ["shell" "./target/${:name}"]})
