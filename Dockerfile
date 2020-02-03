#!/bin/bash
FROM openjdk:11-jre-slim
RUN mkdir -p /opt/app
ENV APP_PATH /opt/app/
ARG ARTIFACT_PATH=./target
COPY "${ARTIFACT_PATH}"/greeting.jar "$APP_PATH"/greeting.jar
WORKDIR "$APP_PATH/"
EXPOSE 8080
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom","-jar","greeting.jar"]
