# Movie Recommendation System

## Live Demo:
    https://memeo.tj-z.com

## Tech Stack:

### Web Frontend: 
    Angular 8

### Web Backend:
    Spring Boot, MySQL (RDS), RESTFul API, Docker

### Data Analysis:
    Scala, Spark, Spark MLlib, Flume, Kafka

### CI/CD:
    AWS S3, Travis CI, AWS CodeDeploy, AWS CodePipeline

## How to run:
### 0. Clone the repository

## 1. Frontend
Run command `cd frontend` to change directory to frontend

Run command `npm install`

Run command `ng serve` to start front end server

Run command `ng build --prod` to build frontend

## 2. Backend
Import backend into IDE and start backend server

Run command `mvn clean package` to build backend into jar file

Build Docker Image. See instruction below

## 3. Data Analysis
Build project based on SBT

Create MySQL tables. See statements in https://github.com/TJZhou/Movie-Recommendation-Memeo/blob/master/config/memeo-table-create.txt

Set up your Database connection properties. (Not pushed due to security consideration)

Run `DataPreProcessApp.scala` to pre-process data, join movie link, movie rating & movie rater to movie.csv file. Original Dataset https://grouplens.org/datasets/movielens/ 

Import CSV data into database

Run `ModelBuildApp.scala` to build Offline ALS recommendation model. After model successfully build, update movie_recommend table in MySql.

Run `SparkStreamApp.scala` to start Spark Streaming. Pull stream data from Kafka 
### (!!REAL TIME MOVIE RECOMMENDATION NOT IMPLEMENTED YET!!)

### 4. Docker
Build Docker Image
`docker build --tag <repository>/<iamge-name>:<version> .`

Push docker image to docker repository
`docker push <repository>/<iamge-name>:<version>`
    
Pull docker image from docker repository
`docker pull <repository>/<iamge-name>:<version>`

Start backend server in docker container 
`docker run --publish <port>:<port> --detach --name <name> <repository>/<iamge-name>:<version>`

### 5. Flume
Make sure you have flume installed
https://flume.apache.org/download.html

Start Flume `flume-ng agent -c conf -f flume2kafka.conf -n a1`

### 6. Kafka
Make sure you have kafka installed
https://kafka.apache.org/downloads

Start Server

`bin/zookeeper-server-start.sh config/zookeeper.properties`

`bin/kafka-server-start.sh config/server.properties`
    
Create Topic

`bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic test`

List & Describe Topic

`bin/kafka-topics.sh --list --bootstrap-server localhost:9092`

`bin/kafka-topics.sh --describe --bootstrap-server localhost:9092 --topic my-replicated-topic`

Reference: https://kafka.apache.org/quickstart