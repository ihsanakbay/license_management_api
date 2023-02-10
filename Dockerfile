FROM amazoncorretto:17-alpine-jdk AS build
MAINTAINER license_management_api
COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN ./mvnw dependency:resolve

COPY src src
RUN ./mvnw package

FROM amazoncorretto:17-alpine-jdk
WORKDIR license_management_api
COPY --from=build target/*.jar license_management_api.jar
ENTRYPOINT ["java", "-jar", "/license_management_api.jar"]