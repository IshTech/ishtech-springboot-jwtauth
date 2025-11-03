# ====== Stage 1: Build ======
FROM eclipse-temurin:25-jdk AS build

COPY . .

RUN echo $(./mvnw help:evaluate -Dexpression=project.version -q -DforceStdout 2>/dev/null) > .projectVersion

RUN ./mvnw -B -q clean install -DskipTests=true

# ====== Stage 2: Runtime ======
FROM eclipse-temurin:25-jre

COPY --from=build .projectVersion .projectVersion

RUN export APP_VERSION=$(cat .projectVersion)

ARG SERVER_PORT=8080

EXPOSE ${SERVER_PORT:-8080}

COPY --from=build ishtech-springboot-jwtauth-web/target/ishtech-springboot-jwtauth-web-${APP_VERSION-*}.jar ishtech-springboot-jwtauth-web.jar

ENTRYPOINT ["java", "-jar", "ishtech-springboot-jwtauth-web.jar"]
