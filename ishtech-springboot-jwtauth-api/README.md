# ishtech-springboot-jwtauth-api

`ishtech-springboot-jwtauth-api` provides reusable JWT-based authentication APIs for Spring Boot applications.

## Overview

This module **does not run** as a standalone Spring Boot application.


It can be included as a **Maven dependency** in any other Spring Boot project to instantly enable:
- APIs for authentication (e.g. signup, signin etc) with JWT tokens
- See [ishtech-springboot-jwtauth-lib](../ishtech-springboot-jwtauth-lib) for included features

## Parent project

[ishtech-springboot-jwtauth](../README.md)


## Usage

To use this library in another project, add:
- For Maven projects, in `pom.xml`

```xml
<dependency>
    <groupId>fi.ishtech.springboot</groupId>
    <artifactId>ishtech-springboot-jwtauth-api</artifactId>
    <version>x.y.z</version>
</dependency>
```

- For Gradle projects, in `build.gradle`

```
implementation("fi.ishtech.springboot:ishtech-springboot-jwtauth-api:x.y.z")
```
- Note: Replace `x.y.z` with appropriate version number, e.g. `1.0.0` or `1.1.0-SNAPSHOT`


## Build and Run

### Maven

#### Local Maven Build

- For Build see [parent project](../README.md#Local Maven Build)
