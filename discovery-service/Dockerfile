FROM amazoncorretto:21

WORKDIR /app

ARG JAR_FILE=target/discovery-service-0.0.1.jar

COPY ${JAR_FILE} discovery-service-0.0.1.jar

ENTRYPOINT ["java", "-jar", "discovery-service-0.0.1.jar"]
