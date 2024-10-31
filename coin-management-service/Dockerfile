FROM amazoncorretto:21

WORKDIR /app

ARG JAR_FILE=target/coin-management-service-0.0.1.jar

COPY ${JAR_FILE} coin-management-service-0.0.1.jar

ENTRYPOINT ["java", "-jar", "coin-management-service-0.0.1.jar"]
