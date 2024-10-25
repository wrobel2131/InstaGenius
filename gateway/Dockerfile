FROM amazoncorretto:21

WORKDIR /app

ARG JAR_FILE=target/gateway-0.0.1.jar

COPY ${JAR_FILE} gateway-0.0.1.jar

ENTRYPOINT ["java", "-jar", "gateway-0.0.1.jar"]
