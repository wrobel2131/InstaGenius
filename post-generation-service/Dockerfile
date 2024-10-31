FROM amazoncorretto:21

WORKDIR /app

ARG JAR_FILE=target/post-generation-service-0.0.1.jar

COPY ${JAR_FILE} post-generation-service-0.0.1.jar

ENTRYPOINT ["java", "-jar", "post-generation-service-0.0.1.jar"]
