# ishtech-springboot-jwtauth
Spring Boot Auth using JWT - parent project

## Tech stack

- JDK 25 (default)
- Other supported JDK versions:
  - JDK 21
  - JDK 17
- Spring Boot: 4.0.x
- Security: JWT
- Database: Supports various databases, see child projects for details
- Database Migration: Flyway
- Containerization: Docker

### Version for each JDK version

- Releases for the default JDK version have plain version numbers, for example `x.y.z`. They are built from the branches `dev` and `main`.
- Releases for another supported JDK version have the same version number with the suffix `-jdkNN`, for example `x.y.z-jdk21` for JDK 21. They are built from the branch `dev-jdkNN`, for example `dev-jdk21`, from the same code, adapted where that JDK version needs it.
- The libraries `ishtech-springboot-jwtauth-lib` and `ishtech-springboot-jwtauth-api` on Maven Central and the Docker image `muneer2ishtech/ishtech-springboot-jwtauth-web` on Docker Hub have the same version; the image tag is the version.
- Use the version that matches your JDK version. Dependency update tools may suggest the version without a suffix as newer; that version needs the default JDK version.

##

[GIT](https://github.com/ishtech/ishtech-springboot-jwtauth)


## Design
- [ishtech-base-jpa](https://github.com/ishtech/ishtech-base-jpa) - Foundational JPA and other base classes

## Project structure

[ishtech-springboot-jwtauth](./README.md)<br>
├── [ishtech-springboot-jwtauth-lib](./ishtech-springboot-jwtauth-lib/README.md)<br>
├── [ishtech-springboot-jwtauth-api](./ishtech-springboot-jwtauth-api/README.md)<br>
└── [ishtech-springboot-jwtauth-web](./ishtech-springboot-jwtauth-web/README.md)<br>


## Usage

1. Add JWT Security to your project
    - If you only need Spring Boot security configuration with JWT, add the `ishtech-springboot-jwtauth-lib` module as a dependency (in your project `pom.xml` or `build.gradle`).

1. Add JWT Security + REST APIs (Signin, Signup, etc.)
    - If you need both JWT security and ready-to-use REST APIs for authentication, add the `ishtech-springboot-jwtauth-api` module as a dependency.

1. Run an independent Spring Boot authentication server
    - If you want a standalone authentication and authorization application, run the `ishtech-springboot-jwtauth-web` Spring Boot application.

## APIs

- For details you can see swagger documentation
    - [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
    - [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)
    - [http://localhost:8080/v3/api-docs.yaml](http://localhost:8080/v3/api-docs.yaml)

- Note: Check and update URI and PORT on which application is running

- For API names and descriptions:
    - See [API-INFO.md](./API-INFO.md)

- For `curl` & `json` request/response samples:
    - See [CURL-INFO.md](./CURL-INFO.md)


## Known Issues
- See [KNOWN-ISSUES.md](./KNOWN-ISSUES.md)


## Build and Run

- Ensure the port, db properties etc are correct in application-xxx.properties

### Maven

#### Local Maven Build

- Build without tests

```
./mvnw clean install -DskipTests
```

- Build with Junit tests

```
./mvnw clean install
```

#### Publish to Maven Central

```
./mvnw clean deploy -DskipTests=true -pl "ishtech-springboot-jwtauth-lib,ishtech-springboot-jwtauth-api" -P gpg -P central-publishing -am
```
