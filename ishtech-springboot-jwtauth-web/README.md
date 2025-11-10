# ishtech-springboot-jwtauth-web

`ishtech-springboot-jwtauth-web` provides Spring Boot applications with JWT based authentication.

## Parent project

[ishtech-springboot-jwtauth](../README.md)

## Overview

This is the **runnable module** — it exposes REST APIs for authentication and user management using JWT.

## Database
- See [DB-SETUP.md](./DB-SETUP.md) for setting up dev database

## Build and Run

- Ensure the port, db properties etc are correct in application-xxx.properties

### Local Maven Build

- For Build see [parent project](../README.md#Local Maven Build)

- Run

```
./mvnw -pl ishtech-springboot-jwtauth-web spring-boot:run -Dspring-boot.run.profiles=dev
```

### Docker

- `docker` commands should be run from [parent project root folder](../)

#### Docker build

```
docker build . \
  --build-arg SERVER_PORT=8080 \
  --build-arg APP_VERSION=$(./mvnw help:evaluate -Dexpression=project.version -q -DforceStdout 2>/dev/null) \
  -t "ishtech-springboot-jwtauth-web:$(./mvnw help:evaluate -Dexpression=project.version -q -DforceStdout 2>/dev/null)"

```
### Run with docker compose

- Docker compose is self contained and has both spring-boot application and mariadb is present, so  you don't need anything else other than docker

- To stop if running

```
docker compose stop

```

- To build and start
    - You can prefix with env vars as in below example

```
SERVER_PORT=8484 DB_PORT=15432 APP_VERSION=$(./mvnw help:evaluate -Dexpression=project.version -q -DforceStdout) \
docker-compose up --build

```
