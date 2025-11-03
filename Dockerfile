# ====== Stage 1: Build ======
FROM eclipse-temurin:25-jdk AS build

COPY . .

RUN ./mvnw clean install -DskipTests=true

# ====== Stage 2: Runtime ======
FROM eclipse-temurin:25-jre

ARG APP_VERSION=0.5.0-SNAPSHOT
ARG SERVER_PORT=8080

EXPOSE ${SERVER_PORT}

COPY --from=build ishtech-springboot-jwtauth-web/target/ishtech-springboot-jwtauth-web-${APP_VERSION}.jar ishtech-springboot-jwtauth-web.jar

ENTRYPOINT ["java", "-jar", "ishtech-springboot-jwtauth-web.jar"]
