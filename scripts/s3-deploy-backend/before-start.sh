#!/bin/bash

docker stop memeo-backend
docker rm memeo-backend
docker rmi tjzhou/memeo:2.0

DIRECTORY=/var/www/memeo
FILE=/var/backend/memeo/memeo-0.0.1-SNAPSHOT.jar

# Make directory if not exists
if [ ! -d "$DIRECTORY" ]; then 
    mkdir $DIRECTORY
fi

# Archive previous file if exists
if test -f "$FILE"; then
    tar -czvf /var/backend/memeo/memeo-0.0.1-SNAPSHOT.tar.gz $FILE
    rm -rf $FILE
fi