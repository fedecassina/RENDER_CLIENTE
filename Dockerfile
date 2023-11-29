# syntax = docker/dockerfile:1.2

# Build stage
FROM maven:3.8.6-openjdk-18 AS build
COPY Cliente .
WORKDIR Cliente
RUN mvn clean package assembly:single -DskipTests

# Package stage
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/Cliente/target/untitled-1.0-SNAPSHOT-jar-with-dependencies.jar App.jar
EXPOSE 8080
CMD ["java", "-classpath", "App.jar", "server.App"]





# syntax = docker/dockerfile:1.2
#
# Build stage
#
#FROM maven:3.8.6-openjdk-18 AS build
#COPY Cliente .
#RUN mvn clean package assembly:single -DskipTests

#
# Package stage
#
#FROM openjdk:17-jdk-slim
#COPY --from=build /target/untitled-1.0-SNAPSHOT-jar-with-dependencies.jar App.jar
# ENV PORT=8080
#EXPOSE 8080
#CMD ["java","-classpath","App.jar","server.App"]