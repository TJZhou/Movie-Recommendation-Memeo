#!/bin/bash
set -e

cd backend

mvn -DMYSQL_SERVER=$MYSQL_SERVER -DMYSQL_PASSWORD=$MYSQL_PASSWORD -DREDIS_SERVER=$REDIS_SERVER clean package

cd ..

# create deploy directory if not exists
mkdir -p deploy-folder-backend
# remove older version of zip files
rm -rf deploy-folder-backend/*

zip -r deploy-folder-backend/memeo-backend.zip scripts/s3-deploy-backend/*

