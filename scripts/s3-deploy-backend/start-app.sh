#!/bin/bash
docker stop memeo-backend
docker rm memeo-backend
docker rmi tjzhou/memeo:1.0
docker pull tjzhou/memeo:1.0
docker run --publish 8071:8071 --detach --name memeo-backend tjzhou/memeo:1.0