(ns datahike-firebase.core-test
  (:require [clojure.test :as t :refer [is are deftest testing]]
            [datahike.api :as d]
            [datahike-firebase.core]
            [malli.generator :as mg]
            [superv.async :refer [<?? S]]))

(def home
  [:map
    [:name string?]
    [:description string?]
    [:rooms pos-int?]
    [:capcity pos-int?]
    [:address
      [:map
        [:street string?]
        [:number int?]
        [:country [:enum "kenya" "lesotho" "south-africa" "italy" "mozambique" "spain" "india" "brazil" "usa" "germany"]]]]])

(defn non-zero [n] (inc (rand-int n)))

(defn random-homes [n] 
  (for [r (range n)]
    (-> (mg/generate home {:size 100 :seed r}) 
        (assoc :num (inc r))
        (assoc :db/id (inc r)))))

(deftest core-test
  (let [config {:store {:backend :firebase
                        :env "FIRE"
                        :root "datahike"}
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

; (deftest ^:integration test-env
;   (let [_ (d/delete-database)]
;     (is (not (d/database-exists?)))
;     (let [_ (d/create-database)
;           conn (d/connect)]

;       (d/transact conn [{:db/ident :name
;                          :db/valueType :db.type/string
;                          :db/cardinality :db.cardinality/one}
;                         {:db/ident :age
;                          :db/valueType :db.type/long
;                          :db/cardinality :db.cardinality/one}])
;       (d/transact conn [{ :db/id 1, :name  "Ivan", :age   15}
;                         { :db/id 2, :name  "Petr", :age   37}
;                         { :db/id 3, :name  "Ivan", :age   37}
;                         { :db/id 4, :age 15}])
;       (is (= (d/q '[:find ?e :where [?e :name]] @conn)
;              #{[3] [2] [1]}))

;       (d/release conn)
;       (is (d/database-exists?))
;       (d/delete-database)
;       (is (not (d/database-exists?))))))