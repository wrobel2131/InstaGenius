package com.instagenius.userservice.infrastructure.adapter;

import com.instagenius.userservice.application.KeycloakResourcePort;
import com.instagenius.userservice.domain.UpdateUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KeycloakResourceAdapter implements KeycloakResourcePort {
    private final RestTemplate restTemplate;


    @Override
    public void updateUser(UUID id, String kcUserId, String kcRealmId, UpdateUser updateUser) {
        Map<String, String>


        restTemplate.put("http://localhost:8080/auth/realms/{realmId}/users/{id}",
                kcRealmId,
                kcUserId
        );
    }

    private String getAccessToken() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Jwt jwt) {
            return jwt.getTokenValue();
        }
        return null;
    }
}
