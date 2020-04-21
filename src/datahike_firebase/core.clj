(ns datahike-firebase.core
  (:require [datahike.store :refer [empty-store delete-store connect-store scheme->index]]
            [hitchhiker.tree.bootstrap.konserve :as kons]
            [konserve-fire.core :as fire]
            [superv.async :refer [<?? S]]))


(defmethod empty-store :fire [config]
  (kons/add-hitchhiker-tree-handlers
   (<?? S (fire/new-fire-store (:db config) :env (:env config) :root (:root config)))))

(defmethod delete-store :fire [config]
  (let [store (<?? S (fire/new-fire-store (:db config) :env (:env config) :root (:root config)))]
    (fire/delete-store store)))

(defmethod connect-store :fire [config]
  (<?? S (fire/new-fire-store (:db config) :env (:env config) :root (:root config))))

(defmethod scheme->index :fire [_]
  :datahike.index/hitchhiker-tree)