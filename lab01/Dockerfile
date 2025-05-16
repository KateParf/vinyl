FROM maven:3.9.9-eclipse-temurin-21 as build

COPY src src
COPY pom.xml pom.xml

RUN mvn clean package dependency:copy-dependencies -DincludeScope=runtime

FROM bellsoft/liberica-openjdk-debian:21

RUN adduser --system spring-boot && addgroup --system spring-boot && adduser spring-boot spring-boot
USER spring-boot

WORKDIR /app

COPY --from=build target/dependency ./lib
COPY --from=build target/vinyl-0.0.1-SNAPSHOT.jar ./application.jar

ENTRYPOINT ["java", "-jar", "./application.jar"]