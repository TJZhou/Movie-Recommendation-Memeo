# Movie Recommendation System

## Live Demo: https://memeo.tj-z.com

## Tech Stack:

### Web Frontend: 
    Angular 8

### Web Backend:
    Spring Boot, MySQL (RDS), RESTFul API, Docker, Redis (ElastiCache)

### Stream Process:
    Flume, Kafka, Spark Streaming

### Data Analysis:
    Scala, Spark, Spark MLlib, K-Means

### CI/CD:
    AWS S3, Travis CI, AWS CodeDeploy, AWS CodePipeline

## How to run:
### 0. Clone the repository

### 1. Frontend
1. Run command `cd frontend` to change directory to frontend

2. Run command `npm install`

3. Run command `ng serve` to start front end server

4. Run command `ng build --prod` to build frontend

### 2. Backend
1. Import backend into IDE and start backend server

2. Run command `mvn clean package` to build backend into jar file

3. Build Docker Image. See instruction below

### 3. Data Analysis
1. Build project based on SBT

2. Create MySQL tables. See statements in https://github.com/TJZhou/Movie-Recommendation-Memeo/blob/master/config/memeo-table-create.txt

3. Set up your Database connection properties. (Not pushed due to security consideration)

4. Run `DataPreProcessApp.scala` to pre-process data, join movie link, movie rating & movie rater to movie.csv file. Original Dataset https://grouplens.org/datasets/movielens/ 

5. Import CSV data into database

6. Run `ModelBuildApp.scala` to build Offline ALS recommendation model. After model successfully build, update movie_recommend table in MySql.

7. Run `SparkStreamApp.scala` to start Spark Streaming. Pull stream data from Kafka and recommend with K-Means

### 4. Docker
1. Build Docker Image
`docker build --tag <repository>/<iamge-name>:<version> .`

2. Push docker image to docker repository
`docker push <repository>/<iamge-name>:<version>`
    
3. Pull docker image from docker repository
`docker pull <repository>/<iamge-name>:<version>`

4. Start backend server in docker container 
`docker run --publish <port>:<port> --detach --name <name> <repository>/<iamge-name>:<version>`

### 5. Flume
1. Make sure you have flume installed
https://flume.apache.org/download.html

2. Start Flume `flume-ng agent -c conf -f flume2kafka.conf -n a1`

### 6. Kafka
1. Make sure you have kafka installed
https://kafka.apache.org/downloads

2. Start Zookeeper Server 
`bin/zookeeper-server-start.sh config/zookeeper.properties`

3. Start Kafka Server 
`bin/kafka-server-start.sh config/server.properties`
    
4. Create Topic
`bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic test`

## Reference: 
1. https://kafka.apache.org/quickstart

2. Himel, M. T., Uddin, M. N., Hossain, M. A., &amp; Jang, Y. M. (2017). Weight based movie recommendation system using K-means algorithm. 2017 International Conference on Information and Communication Technology Convergence (ICTC). doi:10.1109/ictc.2017.8190928