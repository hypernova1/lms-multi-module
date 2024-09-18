FROM openjdk:17-jdk-alpine
ARG JAR_FILE=/core/api/build/libs/api.jar
COPY ${JAR_FILE} api.jar
ENTRYPOINT ["java", "-jar", "/api.jar"]