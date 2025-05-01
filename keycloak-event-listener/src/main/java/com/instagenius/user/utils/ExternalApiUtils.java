package com.instagenius.user.utils;

import jakarta.ws.rs.InternalServerErrorException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.models.KeycloakSession;
import org.keycloak.util.JsonSerialization;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.logging.Logger;

import static jakarta.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static jakarta.ws.rs.core.HttpHeaders.CONTENT_TYPE;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

@UtilityClass
@Slf4j
public class ExternalApiUtils {
    private static final String BEARER_TOKEN = "Bearer ";

    public void performExternalPOSTApiCall(String url, Object requestBody, KeycloakSession keycloakSession) {
        String signedAccessToken = TokenUtils.getAccessToken(keycloakSession);
        log.debug("Signed access token: {}", signedAccessToken);

        HttpClient httpClient = HttpClient.newHttpClient();

        try {
            HttpRequest httpRequest = HttpRequest
                    .newBuilder()
                    .uri(new URI(url))
                    .timeout(Duration.ofSeconds(10))
                    .header(AUTHORIZATION, BEARER_TOKEN + signedAccessToken)
                    .header(CONTENT_TYPE, APPLICATION_JSON)
                    .POST(HttpRequest.BodyPublishers.ofString(JsonSerialization.writeValueAsString(requestBody)))
                    .build();
            log.debug("Sending created user to user-service!");

            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            log.debug("Response from API: {}", response.body());
            log.debug("Response status: {}", response.statusCode());

        } catch (Exception e) {
            log.debug("Exception caught: {}", e.getMessage());
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
                    .header(AUTHORIZATION, BEARER_TOKEN + signedAccessToken)
                    .DELETE()
                    .build();

            log.debug("Deleting user from user-service");
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            log.debug("Response from API: {}", response.body());
        } catch (Exception e) {
            log.debug("Exception caught: {}", e.getMessage());
            throw new InternalServerErrorException(e.getMessage());
        }
        httpClient.close();
    }

    public void performExternalPUTApiCall(String url, Object requestBody, KeycloakSession keycloakSession) {
        String signedAccessToken = TokenUtils.getAccessToken(keycloakSession);
        log.debug("Signed access token: {}", signedAccessToken);

        HttpClient httpClient = HttpClient.newHttpClient();

        try {
            HttpRequest httpRequest = HttpRequest
                    .newBuilder()
                    .uri(new URI(url))
                    .timeout(Duration.ofSeconds(10))
                    .header(AUTHORIZATION, BEARER_TOKEN + signedAccessToken)
                    .header(CONTENT_TYPE, APPLICATION_JSON)
                    .PUT(HttpRequest.BodyPublishers.ofString(JsonSerialization.writeValueAsString(requestBody)))
                    .build();
            log.debug("Updating user to sync user-service!");

            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            log.debug("Response from API: {}", response.body());
            log.debug("Response status: {}", response.statusCode());

        } catch (Exception e) {
            log.debug("Exception caught: {}", e.getMessage());
            throw new InternalServerErrorException(e.getMessage());
        }
        httpClient.close();
    }
}
