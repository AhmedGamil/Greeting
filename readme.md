<h1 align="center"> Greeting microservice </h1> <br>

## Table of Contents

- [Introduction](#introduction)
- [Assumptions](#assumptions)
- [run application](#run)
- [test application](#test)



## Introduction

  - Greeting microservice is used to return message to specific account type.
  - this microservice is built using springboot and is packaged in jar and docker image.
  - the tests is built using <a href="http://rest-assured.io/">rest-assured</a> library   

## run
**run tests using maven**
```console
cd greeting
mvn test
```

**run the application using maven**
```console
cd greeting
mvn spring-boot:run
```
**run the application using docker**
```console
cd greeting
docker build -t greeting:1.0 .
docker tag greeting:1.0 greeting:latest
docker run -p 127.0.0.01:8080:8080 greeting:latest
```
## test from console
**Account=personal ,Id=123 **

```console
curl -X GET "localhost:8080/greeting?account=personal&id=123"
```
**Account=business ,type=small**

```console
curl -X GET "localhost:8080/greeting?account=business&type=small"
```
**Account=business ,type=big**

```console
curl -X GET "localhost:8080/greeting?account=business&type=big"
```