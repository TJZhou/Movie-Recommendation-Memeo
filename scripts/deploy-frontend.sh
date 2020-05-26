#!/bin/bash

set -e

# create deploy directory if not exists
mkdir -p deploy-folder-frontend
# remove older version of zip files
rm -rf deploy-folder-frontend/*

cd frontend
npm install -g @angular/cli
npm install
ng build --prod
cd dist/memeo
zip -r ../../../deploy-folder-frontend/memeo-frontend.zip *
cd ../../../

zip -r -j deploy-folder-frontend/memeo-frontend.zip scripts/s3-deploy-frontend/*