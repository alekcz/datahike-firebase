(ns datahike-firebase.core
  (:require [datahike.store :refer  [empty-store delete-store connect-store scheme->index default-config config-spec]]
            [datahike.config :refer [map-from-env]]
            [hitchhiker.tree.bootstrap.konserve :as kons]
            [konserve-fire.core :as fire]
            [clojure.spec.alpha :as s]
            [superv.async :refer [<?? S]]))

(defmethod empty-store :firebase [config]
  (kons/add-hitchhiker-tree-handlers
    (<?? S (fire/new-fire-store (:env config) :db (:db config) :root (:root config)))))

(defmethod delete-store :firebase [config]
  (let [store (<?? S (fire/new-fire-store (:env config) :db (:db config) :root (:root config)))]
    (fire/delete-store store)))

(defmethod connect-store :firebase [config]
  (<?? S (fire/new-fire-store (:env config) :db (:db config) :root (:root config))))

(defmethod default-config :firebase [config]
  (merge
    (map-from-env :datahike-store-config {:env nil 
                                          :root "datahike"
                                          :db "datahike"})
    config))

(defmethod scheme->index :firebase [_]
  :datahike.index/hitchhiker-tree)

(s/def :datahike.store.firebase/backend #{:firebase})
(s/def :datahike.store.firebase/env (s/or :keyword keyword? :string string? :nil nil?))
(s/def :datahike.store.firebase/root string?)
(s/def :datahike.store.firebase/db (s/or :uri uri? :string string?))
(s/def ::firebase (s/keys :req-un [:datahike.store.firebase/backend]
                          :opt-un [:datahike.store.firebase/env
                                   :datahike.store.firebase/db
                                   :datahike.store.firebase/root]))

(defmethod config-spec :firebase [_] ::firebase)