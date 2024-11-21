package com.instagenius.user.utils;

import lombok.experimental.UtilityClass;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.representations.AccessToken;

import java.time.Instant;
import java.util.UUID;
import java.util.logging.Logger;

@UtilityClass
public class TokenUtils {
    private static final Logger logger = Logger.getLogger(TokenUtils.class.getName());

    public String generateSignedAccessToken(KeycloakSession keycloakSession) {
        String issuerUrl = buildIssuerUrl(keycloakSession);

        AccessToken accessToken = new AccessToken();
        accessToken.id(UUID.randomUUID().toString());
        accessToken.setSubject("event-listener");
        accessToken.issuer(issuerUrl);
        accessToken.addAudience("my-client-id");
        accessToken.issuedNow();
        accessToken.exp(Instant.now().getEpochSecond() + 300L);

        AccessToken.Access realmAccess = new AccessToken.Access();
        realmAccess.addRole("admin");
        accessToken.setRealmAccess(realmAccess);

        logger.info("Generated access token");
        return keycloakSession.tokens().encode(accessToken);
    }

    private String buildIssuerUrl(KeycloakSession keycloakSession) {
        String baseUrl = keycloakSession.getContext().getUri().getBaseUri().toString();
        RealmModel realmModel = keycloakSession.getContext().getRealm();
        return baseUrl + "realms/" + realmModel.getName();
    }
}
