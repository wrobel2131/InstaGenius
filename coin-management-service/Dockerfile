FROM openjdk:21-jdk

WORKDIR /app

ARG JAR_FILE=target/config-service-0.0.1.jar

COPY ${JAR_FILE} config-service-0.0.1.jar

ENTRYPOINT ["java", "-jar", "config-service-0.0.1.jar"]
