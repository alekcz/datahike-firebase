(ns datahike-firebase.graal
  (:require [datahike.api :as d]
            [datahike-firebase.core])
  (:gen-class))

(defn -main []
  (let [config {:store {:backend :firebase
                        :db "http://localhost:9000"
                        :root "datahike-1"}
                :schema-flexibility :read
                :keep-history? false}
        _ (d/delete-database config)]
    (println "exists:" (d/database-exists? config))
    (let [_ (d/create-database config)
          conn (d/connect config)]

      (d/transact conn [{ :db/id 1, :name  "Ivan", :age   15}
                        { :db/id 2, :name  "Petr", :age   37}
                        { :db/id 3, :name  "Ivan", :age   37}
                        { :db/id 4, :age 15}])
      (println "query:" (d/q '[:find ?e :where [?e :name]] @conn) #{[3] [2] [1]})

      (d/release conn)
      (println "exists:" (d/database-exists? config))
      (d/delete-database config)
      (println "exists:" (d/database-exists? config)))))       
