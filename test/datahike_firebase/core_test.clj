(ns datahike-firebase.core-test
  (:require [clojure.test :as t :refer [is are deftest testing]]
            [datahike.api :as d]
            [datahike-firebase.core]
            ;[datahike-postgres.core]
            [malli.generator :as mg]))

(def home
  [:map
    [:db/id [:and int? [:> 10]]]
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
    (mg/generate home {:size (+ r 11) :seed r})))

(deftest test-firebase-store
  (let [config {:backend :fire 
                :env :fire
                :db "alekcz-dev"
                :root (str "/datahike-firebase" (rand-int 100))}
        _ (d/delete-database config)]
    (is (not (d/database-exists? config)))
    (let [_ (d/create-database config :schema-on-read true)
          conn (d/connect config)
          homes (random-homes 10)]
      
      (time (d/transact conn (into [] homes)))
      
      (is (= (set (for [h (filter #(pos? (:rooms %)) homes)] 
                [(:name h) (:rooms h)]))  
          (d/q '[:find  ?n ?r
                  :where  [?e :name ?n]
                          [?e :rooms ?r]
                          [(pos? ?r)]] @conn)))

      (d/release conn)
      (is (d/database-exists? config))
      (d/delete-database config))))