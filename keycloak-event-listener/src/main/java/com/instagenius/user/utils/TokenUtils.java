package com.instagenius.user.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.InternalServerErrorException;
import lombok.experimental.UtilityClass;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.keycloak.connections.httpclient.HttpClientProvider;
import org.keycloak.models.*;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.services.util.DefaultClientSessionContext;
import org.keycloak.util.JsonSerialization;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;


@UtilityClass
public class TokenUtils {
    private static final Logger logger = Logger.getLogger(TokenUtils.class.getName());

    public String getAccessToken(KeycloakSession keycloakSession) {
        RealmModel realmModel = keycloakSession.getContext().getRealm();
        String tokenEndpointUrl = "http://host.docker.internal:8180/realms/" + realmModel.getName() + "/protocol/openid-connect" +
                "/token";

        logger.info("Token endpoint URL: " + tokenEndpointUrl);

        HttpClient httpClient = HttpClient.newHttpClient();

        String formData = buildFormDataForTokenRetrieving();

        try {
            HttpRequest tokenRequest = HttpRequest.newBuilder()
                                                  .uri(new URI(tokenEndpointUrl))
                                                  .timeout(Duration.ofSeconds(10))
                                                  .header("Content-Type", "application/x-www-form-urlencoded")
                                                  .POST(HttpRequest.BodyPublishers.ofString(formData))
                                                  .build();

            HttpResponse<String> response = httpClient.send(tokenRequest, HttpResponse.BodyHandlers.ofString());

            logger.info("Token response status: " + response.statusCode());
            logger.info("Token response body: " + response.body());

            if (response.statusCode() == 200 || response.statusCode() == 201) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(response.body());
                httpClient.close();
                return jsonNode.get("access_token").asText();
            } else {
                logger.severe("Failed to get access token. Status code: " + response.statusCode());
                throw new InternalServerErrorException("Failed to retrieve token from Keycloak: " + response.body());
            }

        } catch (Exception e) {
            logger.severe("Error while retrieving access token: " + e.getMessage());
            throw new InternalServerErrorException("Exception while retrieving token: " + e.getMessage(), e);
        }
    }

    private String buildFormDataForTokenRetrieving() {
        String clientId = System.getenv("EVENT_LISTENER_CLIENT_ID");
        String clientSecret = System.getenv("EVENT_LISTENER_CLIENT_SECRET");

        if (clientId == null || clientSecret == null) {
            throw new InternalServerErrorException("Client ID or Client Secret not found in environment variables");
        }

        return "grant_type=client_credentials"
                + "&client_id=" + URLEncoder.encode(clientId, StandardCharsets.UTF_8)
                + "&client_secret=" + URLEncoder.encode(clientSecret, StandardCharsets.UTF_8);
    }

}
