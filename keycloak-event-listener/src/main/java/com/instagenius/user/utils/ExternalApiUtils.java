package com.instagenius.user.utils;

import jakarta.ws.rs.InternalServerErrorException;
import lombok.experimental.UtilityClass;
import org.keycloak.models.KeycloakSession;
import org.keycloak.util.JsonSerialization;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.logging.Logger;

@UtilityClass
public class ExternalApiUtils {
    private static final Logger logger = Logger.getLogger(ExternalApiUtils.class.getName());

    public void performExternalPOSTApiCall(String url, Object requestBody, KeycloakSession keycloakSession) {
        String signedAccessToken = TokenUtils.getAccessToken(keycloakSession);
        logger.info("Signed access token: " + signedAccessToken);

        HttpClient httpClient = HttpClient.newHttpClient();

        try {
            HttpRequest httpRequest = HttpRequest
                    .newBuilder()
                    .uri(new URI(url))
                    .timeout(Duration.ofSeconds(10))
                    .header("Authorization", "Bearer " + signedAccessToken)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(JsonSerialization.writeValueAsString(requestBody)))
                    .build();
            logger.info("Sending created user to user-service!");

            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            logger.info("Response from API: " + response.body());
            logger.info("Response status: " + response.statusCode());

        } catch (Exception e) {
            logger.warning("Exception caught: " + e.getMessage());
            throw new InternalServerErrorException(e.getMessage());
        }
        httpClient.close();
    }

    public void performExternalDELETEApiCall(String url, KeycloakSession keycloakSession) {
        String signedAccessToken = TokenUtils.getAccessToken(keycloakSession);

        HttpClient httpClient = HttpClient.newHttpClient();

        try {
            HttpRequest httpRequest = HttpRequest
                    .newBuilder()
                    .uri(new URI(url))
                    .timeout(Duration.ofSeconds(10))
                    .header("Authorization", "Bearer " + signedAccessToken)
                    .DELETE()
                    .build();

            logger.info("Deleting user from user-service");
//            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
//            logger.info("Response from API: " + response.body());
        } catch (Exception e) {
            logger.warning("Exception caught: " + e.getMessage());
            throw new InternalServerErrorException(e.getMessage());
        }
        httpClient.close();
    }

    public void performExternalPUTApiCall(String url, Object requestBody, KeycloakSession keycloakSession) {
        String signedAccessToken = TokenUtils.getAccessToken(keycloakSession);

        HttpClient httpClient = HttpClient.newHttpClient();

        try {
            HttpRequest httpRequest = HttpRequest
                    .newBuilder()
                    .uri(new URI(url))
                    .timeout(Duration.ofSeconds(10))
                    .header("Authorization", "Bearer " + signedAccessToken)
                    .PUT(HttpRequest.BodyPublishers.ofString(JsonSerialization.writeValueAsString(requestBody)))
                    .build();

            logger.info("Updating user to user-service");
//            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
//            logger.info("Response from API: " + response.body());
        } catch (Exception e) {
            logger.warning("Exception caught: " + e.getMessage());
            throw new InternalServerErrorException(e.getMessage());
        }
        httpClient.close();
    }
}
