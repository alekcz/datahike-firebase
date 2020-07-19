#!/bin/bash
DATAHIKE_STORE_BACKEND=firebase DATAHIKE_STORE_CONFIG='{:db "http://localhost:9000" :root "datahike-3"}' lein cloverage --codecov