FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
ENV FOO=BAR
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]