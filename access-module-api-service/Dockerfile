FROM adoptopenjdk/openjdk11:jdk-11.0.11_9-alpine-slim
VOLUME /tmp
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} access-module-api-service-2.0.0.jar
ENTRYPOINT ["java","-jar","/access-module-api-service-2.0.0.jar"]
EXPOSE 8080/tcp
