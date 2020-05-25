#!/bin/bash
set -e

cd backend
# create and push the newest docker image to docker hub
docker build --tag tjzhou/memeo:1.0 .
echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USERNAME" --password-stdin
docker push tjzhou/memeo:1.0
cd ..