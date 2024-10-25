FROM amazoncorretto:21

WORKDIR /app

ARG JAR_FILE=target/post-management-service-0.0.1.jar

COPY ${JAR_FILE} post-management-service-0.0.1.jar

ENTRYPOINT ["java", "-jar", "post-management-service-0.0.1.jar"]
