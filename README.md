# ishtech-springboot-jwtauth

## Tech stack
- java - 25
- spring-boot - 3.5.7

## 

[GIT](https://github.com/ishtech/ishtech-springboot-jwtauth)

## Project structure

[ishtech-springboot-jwtauth](https://github.com/IshTech/ishtech-springboot-jwtauth/blob/main/README.md)<br>
├── [ishtech-springboot-jwtauth-lib](https://github.com/IshTech/ishtech-springboot-jwtauth/blob/main/ishtech-spring-boot-jwt-lib/README.md)<br>
├── [ishtech-springboot-jwtauth-api](https://github.com/IshTech/ishtech-springboot-jwtauth/blob/main/ishtech-spring-boot-jwt-api/README.md)<br>
└── [ishtech-springboot-jwtauth-web](https://github.com/IshTech/ishtech-springboot-jwtauth/blob/main/ishtech-spring-boot-jwt-web/README.md)<br>


## APIs

| Module  | Type            | HTTP  | URL                          | Description |
|---------|-----------------|-------|------------------------------|-------------|
| API Doc | OpenAPI         | GET   | /v3/api-docs                 | Swagger generated API Documentation                 |
| API Doc | Swagger         | GET   | /swagger-ui.html             | Swagger Documentation Home                          |
| Auth    | Signin          | GET   | /api/v1/auth/signin          | Authenticate user with password and returns JWT     |
| Auth    | Signup          | GET   | /api/v1/auth/signup          | Registers new user                                  |
| Auth    | Update Password | GET   | /api/v1/auth/update-password | Updates password after signing in                   |
| Auth    | Forgot Password | GET   | /api/v1/auth/forgot-password | Creates and sends reset password link               |
| Auth    | Update Password using Reset Token| GET   | /api/v1/auth/reset-password | Sets new password using reset token |

- For details you can see swagger documentation
    - [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
    - [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)
- If running on different server or port change their values in URL

- See API request/response samples here in [API-INFO.md](./API-INFO.md)


## Build & Run

### Local Build

#### Build using Maven
- You can make build with or without running tests

```
mvn clean package -DskipTests=true
```

#### To get source code and javadoc of dependencies

```
mvn dependency:resolve -Dclassifier=sources;javadoc

mvn dependency:tree

```


#### Docker build

```
docker build \
  --build-arg APP_VERSION=x.y.z \
  --build-arg SERVER_PORT=8080 \
  -t ishtech-springboot-jwtauth-web:x.y.z .
```
- Note: Replace `x.y.z` with appropriate version number, e.g. `1.0.0` or `1.1.0-SNAPSHOT`

### Local Run

#### Run using Maven

```
./mvnw -pl ishtech-springboot-jwtauth-web spring-boot:run -Dspring-boot.run.profiles=dev
```

#### Docker Run


```
 docker run -p 8080:8080 ishtech-springboot-jwtauth-web:x.y.z
```
