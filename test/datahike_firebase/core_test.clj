(ns datahike-firebase.core-test
  (:require [clojure.test :as t :refer [is deftest]]
            [datahike.api :as d]
            [datahike-firebase.core]))

(deftest core-test
  (let [config {:store {:backend :firebase
                        :db "http://localhost:9000"
                        :root "datahike-1"}
                :schema-flexibility :read
                :keep-history? false}
        _ (d/delete-database config)]
    (is (not (d/database-exists? config)))
    (let [_ (d/create-database config)
          conn (d/connect config)]

      (d/transact conn [{ :db/id 1, :name  "Ivan", :age   15}
                        { :db/id 2, :name  "Petr", :age   37}
                        { :db/id 3, :name  "Ivan", :age   37}
                        { :db/id 4, :age 15}])
      (is (= (d/q '[:find ?e :where [?e :name]] @conn)
             #{[3] [2] [1]}))

      (d/release conn)
      (is (d/database-exists? config))
      (d/delete-database config)
      (is (not (d/database-exists? config))))))       

(deftest env-test
  (let [config {:store {:backend :firebase
                        :env :fire
                        :db nil
                        :root "datahike-2"}
                :schema-flexibility :read
                :keep-history? false}
        _ (d/delete-database config)]
    (is (not (d/database-exists? config)))
    (let [_ (d/create-database config)
          conn (d/connect config)]

      (d/transact conn [{ :db/id 1, :name  "Ivan", :age   15}
                        { :db/id 2, :name  "Petr", :age   37}
                        { :db/id 3, :name  "Ivan", :age   37}
                        { :db/id 4, :age 15}])
      (is (= (d/q '[:find ?e :where [?e :name]] @conn)
             #{[3] [2] [1]}))

      (d/release conn)
      (is (d/database-exists? config))
      (d/delete-database config)
      (is (not (d/database-exists? config))))))   

(deftest direct-env-test
  (let [_ (d/delete-database)]
    (is (not (d/database-exists?)))
    (let [_ (d/create-database)
          conn (d/connect)]
      
      (d/transact conn [{:db/ident :name
                         :db/valueType :db.type/string
                         :db/cardinality :db.cardinality/one}
                        {:db/ident :age
                         :db/valueType :db.type/long
                         :db/cardinality :db.cardinality/one}])
      (d/transact conn [{ :db/id 1, :name  "Ivan", :age   15}
                        { :db/id 2, :name  "Petr", :age   37}
                        { :db/id 3, :name  "Ivan", :age   37}
                        { :db/id 4, :age 15}])
      (is (= (d/q '[:find ?e :where [?e :name]] @conn)
             #{[3] [2] [1]}))

      (d/release conn)
      (is (d/database-exists?))
      (d/delete-database)
      (is (not (d/database-exists?))))))             