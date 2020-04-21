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
    (-> (mg/generate home {:size (+ (mod r 10) 11) :seed r}) 
        (assoc :num (inc r))
        (assoc :db/id (inc r)))))

(deftest test-firebase-store
  (let [config {:backend :fire  :env :fire :db "alekcz-dev" :root (str "/datahike-firebase" (rand-int 100))}
        _ (d/delete-database config)]
    (is (not (d/database-exists? config)))
    (let [_ (d/create-database config :schema-on-read true)
          conn (d/connect config)
          homes (random-homes 1000)]
      
      (d/transact conn (into [] homes))
      
      (let [query (into [] (d/q '[:find ?id :where [_ :num ?id]] @conn))
            query2 (into [] (d/q '[:find ?n  :where [_ :name ?n]] @conn))]
        (is (= (count homes) (count query)))
        (is (= (-> (map :num homes) flatten sort) (-> query flatten sort)))
        (is (= (-> (map :name homes) flatten distinct sort) (-> query2 flatten distinct sort)))

        (d/release conn)
        (is (d/database-exists? config))
        (d/delete-database config)))))