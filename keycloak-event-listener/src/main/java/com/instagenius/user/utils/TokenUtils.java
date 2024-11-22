package com.instagenius.user.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.InternalServerErrorException;
import lombok.experimental.UtilityClass;
import org.keycloak.models.*;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.logging.Logger;


@UtilityClass
public class TokenUtils {
    private static final Logger logger = Logger.getLogger(TokenUtils.class.getName());

    public String getAccessToken(KeycloakSession keycloakSession) {
        RealmModel realmModel = keycloakSession.getContext().getRealm();
        String tokenEndpointUrl = keycloakSession
                .getContext()
                .getUri()
                .getBaseUriBuilder()
                .path("/realms/{realm-name}/protocol/openid-connect/token")
                .build(realmModel.getName())
                .toString();

        HttpClient httpClient = HttpClient.newHttpClient();

        String formData = buildFormDataForTokenRetrieving();

        try {
            HttpRequest tokenRequest = HttpRequest
                    .newBuilder()
                    .uri(new URI(tokenEndpointUrl))
                    .timeout(Duration.ofSeconds(10))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(formData))
                    .build();

            HttpResponse<String> response = httpClient.send(tokenRequest, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200 || response.statusCode() == 201) {
                logger.info("Successfully got access token from Keycloak");
                // Parse the JSON response to extract the access token
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(response.body());

                httpClient.close();
                return jsonNode.get("access_token").asText();
            } else {
                throw new InternalServerErrorException("Failed to get access token from Keycloak");
            }

        } catch (Exception e) {
            logger.warning("Exception caught: " + e.getMessage());
            throw new InternalServerErrorException(e.getMessage());
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
