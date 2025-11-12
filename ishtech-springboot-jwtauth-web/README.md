# ishtech-springboot-jwtauth-web

`ishtech-springboot-jwtauth-web` provides Spring Boot applications with JWT based authentication.

## Overview

This is the **runnable module** — it exposes REST APIs for authentication and user management using JWT.


## Parent project

[ishtech-springboot-jwtauth](../README.md)


## Database
- See [DB-SETUP.md](./DB-SETUP.md) for setting up dev database


## Build and Run

- Ensure the port, db properties etc are correct in application-xxx.properties

### Maven

#### Local Maven Build

- For Build see [parent project](../README.md#Local Maven Build)

#### Local Maven Run

```
./mvnw -pl ishtech-springboot-jwtauth-web spring-boot:run -Dspring-boot.run.profiles=dev
```

### Docker

- `docker` commands should be run from [parent project root folder](../)

#### Docker build

- arg for custom `SERVER_PORT` is optional, you can change to desired value or skip, if skipped it will use default `8080`

```
docker build . \
  --build-arg SERVER_PORT=8181 \
  --build-arg APP_VERSION=$(./mvnw help:evaluate -Dexpression=project.version -q -DforceStdout 2>/dev/null) \
  -t "ishtech-springboot-jwtauth-web:$(./mvnw help:evaluate -Dexpression=project.version -q -DforceStdout 2>/dev/null)"

```
#### Run with docker compose

- Docker compose is self contained and has both spring-boot application and mariadb is present, so  you don't need anything else other than docker

- To stop if running
    - `docker compose stop`

- To stop and remove including volumes and built images
    - `docker compose down -v --rmi=local`

- To build and start
    - You can prefix with env vars as in below example
    - Below args are optional, you can change to desired value or skip, if skipped they will use default value
        - `DB_PORT` if skipped DB will be exposed on default `3306`
        - `SERVER_PORT_REMOTE` if skipped spring-boot app will run on default `8080`
        - `SERVER_PORT_LOCAL` if skipped spring-boot app will be exposed on default `8080`

```
SERVER_PORT_LOCAL=8181 DB_PORT=15432 APP_VERSION=$(./mvnw help:evaluate -Dexpression=project.version -q -DforceStdout) \
docker compose up --build

```
