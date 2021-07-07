#!/bin/bash
chmod +x ./native.sh        
firebase emulators:exec --only database ./native.sh