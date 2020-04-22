# datahike-firebase

[Datahike](https://github.com/replikativ/datahike) with [Firebase](https://firebase.google.com/products/realtime-database) as data storage.

## Status
![master](https://github.com/alekcz/datahike-firebase/workflows/master/badge.svg) [![codecov](https://codecov.io/gh/alekcz/datahike-firebase/branch/master/graph/badge.svg)](https://codecov.io/gh/alekcz/datahike-firebase)   

_Pre-alpha_

## Prerequisites

For datahike-firebase you will need to create a Realtime Database on Firebase and store the service account credentials in the an environment variable.

## Usage

[![Clojars Project](https://img.shields.io/clojars/v/alekcz/datahike-firebase.svg)](https://clojars.org/alekcz/datahike-firebase)

`[alekcz/datahike-firebase "0.1.0"]`

After including the datahike API and the datahike-firebase namespace, you can use the Firebase backend now using the keyword `:fire`

```clojure
(ns project.core
  (:require [datahike.api :as d]
            [datahike-firebase.core]))

;; Create a config map with firebase as storage medium
(def config 
      {:backend :fire  
       :env "GOOGLE_APPLICATION_CREDENTIALS" ;environment variable with services account details 
       :db "db-name" 
       :root "/datahike-firebase"})

;; Create a database at this place, by default configuration we have a strict
;; schema and temporal index
(d/create-database config)

(def conn (d/connect config))

;; The first transaction will be the schema we are using:
(d/transact conn [{:db/ident :name
                   :db/valueType :db.type/string
                   :db/cardinality :db.cardinality/one }
                  {:db/ident :age
                   :db/valueType :db.type/long
                   :db/cardinality :db.cardinality/one }])

;; Let's add some data and wait for the transaction
(d/transact conn [{:name  "Alice", :age   20 }
                  {:name  "Bob", :age   30 }
                  {:name  "Charlie", :age   40 }
                  {:age 15 }])

;; Search the data
(d/q '[:find ?e ?n ?a
       :where
       [?e :name ?n]
       [?e :age ?a]]
  @conn)
;; => #{[3 "Alice" 20] [4 "Bob" 30] [5 "Charlie" 40]}

;; Clean up the database if it is not needed any more
(d/delete-database config)
```

## License

Copyright © 2020 Alexander Oloo

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
