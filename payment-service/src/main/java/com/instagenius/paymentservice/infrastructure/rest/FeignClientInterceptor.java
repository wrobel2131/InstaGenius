package com.instagenius.paymentservice.infrastructure.rest;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

@RequiredArgsConstructor
public class FeignClientInterceptor implements RequestInterceptor {
    private final OAuth2AuthorizedClientManager authorizedClientManager;
    @Override
    public void apply(RequestTemplate requestTemplate) {
        String token = extractTokenFromSecurityContext();

        if (token == null) {
            // If no token in the SecurityContext, fallback to client credentials flow
            token = fetchTokenUsingClientCredentials();
        }

        if (token != null) {
            requestTemplate.header("Authorization", "Bearer " + token);
        }
    }

    private String fetchTokenUsingClientCredentials() {
        OAuth2AuthorizeRequest oAuth2AuthorizeRequest =
                OAuth2AuthorizeRequest.withClientRegistrationId("keycloak")
                                      .principal("payment-service")
                                      .build();
        OAuth2AuthorizedClient oAuth2AuthorizedClient = authorizedClientManager.authorize(oAuth2AuthorizeRequest);

        if(oAuth2AuthorizedClient != null && oAuth2AuthorizedClient.getAccessToken() != null) {
            return oAuth2AuthorizedClient.getAccessToken().getTokenValue();
        }

        return null;
    }

    private String extractTokenFromSecurityContext() {
            JwtAuthenticationToken authentication =
                    (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

            if (authentication != null && authentication.getToken() != null) {
                return authentication.getToken().getTokenValue();
            }
        return null;
    }
}
