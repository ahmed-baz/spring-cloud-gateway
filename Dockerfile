FROM maven:3.8.7-openjdk-18 AS build
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package -DskipTests

FROM openjdk:17-alpine AS runtime
LABEL maintainer="developer.baz@gmail.com"
WORKDIR /app

ARG PROFILE=dev
COPY --from=build build/target/*.jar /app/gateway-server.jar


EXPOSE 8222
ENV ACTIVE_PROFILE=${PROFILE}

ENTRYPOINT ["java","-jar", "-Dspring.profiles.active=${ACTIVE_PROFILE}","gateway-server.jar"]