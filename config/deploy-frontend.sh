#!/bin/bash

set -e

cd frontend
npm install
ng build --prod
cd ..
# create deploy directory if not exists
mkdir -p deploy_folder
# remove older version of zip files
rm -rf deploy_folder/*
cd frontend/dist/memeo
zip -r ../../../deploy_folder/memeo.zip *
cd ../../../
zip -r deploy_folder/memeo.zip scripts/* appspec.yml