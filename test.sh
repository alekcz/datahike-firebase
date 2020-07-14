#!/bin/bash
chmod +x ./helper.sh        
firebase emulators:exec --only database ./helper.sh
