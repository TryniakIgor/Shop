FROM adoptopenjdk/openjdk11:alpine-jre
MAINTAINER Tryniak Igor
ARG JAR_FILE=target/*.jar
COPY shop-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]