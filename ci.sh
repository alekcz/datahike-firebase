#!/bin/bash
chmod +x ./test.sh        
firebase emulators:exec --only database ./test.sh
