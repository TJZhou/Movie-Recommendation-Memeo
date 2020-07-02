#!/bin/bash

cd /var/backend/memeo
nohup java -jar memeo-0.0.1-SNAPSHOT.jar \
--spring.config.additional-location=/var/backend/properties/application-memeo.properties \
>/dev/null 2>&1 &