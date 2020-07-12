(ns datahike-firebase.core
  (:require [datahike.store :refer [empty-store delete-store connect-store scheme->index default-config config-spec]]
            [datahike.config :refer [map-from-env]]
            [hitchhiker.tree.bootstrap.konserve :as kons]
            [konserve-fire.core :as fire]
            [clojure.spec.alpha :as s]
            [superv.async :refer [<?? S]]))

(defmethod empty-store :firebase [config]
  (kons/add-hitchhiker-tree-handlers
   (<?? S (fire/new-fire-store (:env config) :root (:root config)))))

(defmethod delete-store :firebase [config]
  (let [store (<?? S (fire/new-fire-store (:env config) :root (:root config)))]
    (fire/delete-store store)))

(defmethod connect-store :firebase [config]
  (<?? S (fire/new-fire-store (:env config) :root (:root config))))

(defmethod default-config :firebase [config]
  (merge
    (map-from-env :datahike-store-config {:env "FIRE" ;nil
                                          :root "datahike"})
    config))

(defmethod scheme->index :firebase [_]
  :datahike.index/hitchhiker-tree)

(s/def :datahike.store.firebase/backend #{:firebase})
(s/def :datahike.store.firebase/env string?)
(s/def :datahike.store.firebase/root string?)
(s/def ::jdbc (s/keys :req-un [:datahike.store.firebase/backend
                               :datahike.store.firebase/env]
                      :opt-un [:datahike.store.firebase/root]))

(defmethod config-spec :jdbc [_] ::firebase)