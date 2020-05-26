# For Java 8, try this
FROM openjdk:8-jdk-alpine

# Refer to Maven build -> finalName
ARG JAR_FILE=target/memeo-0.0.1-SNAPSHOT.jar

EXPOSE 8071

# cd /opt/app
WORKDIR /opt/app

# make directory for user log
RUN mkdir -p /opt/app/log-user

# cp target/spring-boot-web.jar /opt/app/app.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/urandom", "-Dspring.config.additional-location=/opt/app/config/application-prod.properties", "-jar","app.jar"]