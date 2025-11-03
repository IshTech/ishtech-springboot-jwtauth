# ishtech-springboot-jwtauth-lib

`ishtech-springboot-jwtauth-lib` provides reusable JWT-based authentication and security components for Spring Boot applications.

## Parent project

[ishtech-springboot-jwtauth](https://github.com/ishtech/ishtech-springboot-jwtauth) - [README](../README.md)

## Overview

It can be included as a **Maven dependency** in any other Spring Boot project to instantly enable:
- User authentication with JWT tokens  
- Password management (update, reset, forgot)  
- Role and authority handling  
- Integration with Spring Security  

This module **does not run** as a standalone Spring Boot application.

---

## How to use

### Maven Dependency

To use this library in another project, add:

```xml
<dependency>
    <groupId>fi.ishtech.springboot</groupId>
    <artifactId>ishtech-springboot-jwtauth-lib</artifactId>
    <version>x.y.z</version>
</dependency>
```

- Note: Replace `x.y.z` with appropriate version number, e.g. `1.0.0` or `1.1.0-SNAPSHOT`
