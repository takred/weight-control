#FROM openjdk:8-jdk-alpine
FROM adoptopenjdk:11-jre-hotspot
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENV BOT_TOKEN="undefined token"
ENTRYPOINT ["java","-jar","/app.jar", "-Dbot_token=${BOT_TOKEN}"]