#!/bin/bash

DIRECTORY=/var/backend/memeo
FILE=/var/backend/memeo/memeo-0.0.1-SNAPSHOT.jar

# Make directory if not exists
if [ ! -d "$DIRECTORY" ]; then 
    mkdir $DIRECTORY
fi

# Archive previous file if exists
if test -f "$FILE"; then
    tar -czvf /var/backend/memeo/memeo-0.0.1-SNAPSHOT.tar.gz $FILE
    pid=$(ps -ef | grep [m]emeo | awk '{print $2}')
    kill -9 $pid
    rm -rf $FILE
fi