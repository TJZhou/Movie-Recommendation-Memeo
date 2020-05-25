#!/bin/bash
set -e

cd backend

# no longer need to include test any more. Coz we've already passed the test stages
mvn -DMYSQL_SERVER=$MYSQL_SERVER -DMYSQL_PASSWORD=$MYSQL_PASSWORD -DREDIS_SERVER=$REDIS_SERVER -DskipTests clean package
# build docker image
docker build --tag tjzhou/memeo:1.0 .
echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USERNAME" --password-stdin
docker push tjzhou/memeo:1.0
cd ..

# create deploy directory if not exists
mkdir -p deploy-folder-backend
# remove older version of zip files
rm -rf deploy-folder-backend/*

zip -r -j deploy-folder-backend/memeo-backend.zip scripts/s3-deploy-backend/*