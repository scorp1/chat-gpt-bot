FROM maven:3.9-amazoncorretto-21 as build

COPY pom.xml .
COPY src ./src

RUN mvn package install -DskipTests

FROM openjdk:21-jdk-slim

COPY --from=build /target/gpt-tg-bot-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]