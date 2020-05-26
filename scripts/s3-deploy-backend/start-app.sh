#!/bin/bash

docker pull tjzhou/memeo:2.0
docker run --publish 8071:8071 --detach -v /var/backend/memeo/config:/opt/app/config --name memeo-backend tjzhou/memeo:2.0