#!/bin/bash

set -e

function printhelp() {
    echo "Usage: ./app <command> <database password>"
    echo "Available commands: "
    echo "   (b)uild-(b)ackend:         builds backend (skip tests/assembly)"
    echo "   (b)uild-(f)rontend:        builds frontend (skip tests/assembly)"
    echo "   (t)est-(b)ackend:          unit tests backend"
    echo "   (t)est-(f)rontend:         unit tests frontend via maven executing ember"
    echo "   (s)tart-(f)rontend:        start ember server"
    echo "   (s)tart-(b)ackend:         start backend serve"
    echo "   (p)ackage-(f)rontend:      package frontend /dist folder and s3 deployment script"
    echo "   (p)ackage-(b)ackend:       package backend jar file and s3 deployment script"
}

COMMAND=$1
MAVEN_CLI_OPTS=$2

if [ -z $COMMAND ]; then
  printhelp
fi

if [[ $COMMAND == "bb" || $COMMAND == "build-backend" ]]; then
  echo "building backend..."
  cd backend
  mvn clean package -Dmaven.test.skip=true $2
  cd ..

elif [[ $COMMAND == "bf" || $COMMAND == "build-frontend" ]]; then
  echo "building frontend..."
  cd frontend
  npm install
  cd ..

# make sure test and start command is called after building
elif [[ $COMMAND == "tb" || $COMMAND == "test-backend" ]]; then
  echo "testing backend..."
  cd backend
  mvn test $2
  cd ..

elif [[ $COMMAND == "tf" || $COMMAND == "test-frontend" ]]; then
  echo "testing frontend..."
#   cd frontend
#   ng test
#   cd ..

elif [[ $COMMAND == "sb" || $COMMAND == "start-backend" ]]; then
  echo "starting backend..."
  cd backend
  mvn spring-boot:run $2
  cd ..

elif [[ $COMMAND == "sf" || $COMMAND == "start-frontend" ]]; then
  echo "starting frontend..."
  cd frontend
  ng serve
  cd ..

elif [[ $COMMAND == "pf" || $COMMAND == "package-frontend" ]]; then
  echo "packaging frontend..."
  cd frontend
  npm install -g @angular/cli@8.3.26
  ng build --prod
  cd ./dist/memeo
  zip -r memeo-frontend.zip *
  cd ../../../
  zip -r -j ./frontend/dist/memeo/memeo-frontend.zip ./scripts/s3-deploy-frontend
  # create frontend deployment folder
  mkdir deploy-folder-frontend
  mv ./frontend/dist/memeo/memeo-frontend.zip deploy-folder-frontend

elif [[ $COMMAND == "pb" || $COMMAND == "package-backend" ]]; then
  echo "packaging backend..."
  cd backend
  zip -j memeo-backend.zip ./target/memeo-0.0.1-SNAPSHOT.jar
  cd ..
  zip -r -j ./backend/memeo-backend.zip ./scripts/s3-deploy-backend/*
  # create backend deployment folder
  mkdir deploy-folder-backend
  mv ./backend/memeo-backend.zip deploy-folder-backend
  
else
  echo "Unknown command: $COMMAND"
  printhelp
fi
