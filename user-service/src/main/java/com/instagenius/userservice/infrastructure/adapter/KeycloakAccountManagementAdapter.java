package com.instagenius.userservice.infrastructure.adapter;

import com.instagenius.userservice.application.KeycloakAccountManagementPort;
import com.instagenius.userservice.domain.UpdateUser;
import com.instagenius.userservice.infrastructure.dto.UpdateUserAccountRequestDto;
import com.instagenius.userservice.infrastructure.exception.FailedUserUpdateException;
import jakarta.ws.rs.NotAuthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KeycloakAccountManagementAdapter implements KeycloakAccountManagementPort {
    private final RestTemplate restTemplate;


    @Override
    public void updateUser(UUID id, UpdateUser updateUser) {
        String accessToken = getAccessToken().orElseThrow(() -> new NotAuthorizedException("No access token " +
                                                                                                   "available!"));
        String url = "http://localhost:8180/auth/realms/instagenius/account/";
        HttpEntity<UpdateUserAccountRequestDto> entity = getUpdateUserAccountEntity(
                id, updateUser, accessToken);

        ResponseEntity<?> responseEntity = restTemplate.postForEntity(url, entity, Void.class);
        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            throw new FailedUserUpdateException("Failed to update user!");
        }
    }

    private static HttpEntity<UpdateUserAccountRequestDto> getUpdateUserAccountEntity(
            UUID id, UpdateUser updateUser, String accessToken) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", "Bearer " + accessToken);
        UpdateUserAccountRequestDto updateUserAccountRequestDto = new UpdateUserAccountRequestDto(
                id.toString(),
                updateUser.username(),
                updateUser.firstName(),
                updateUser.lastName(),
                updateUser.email());
        return new HttpEntity<>(updateUserAccountRequestDto, headers);
    }

    private Optional<String> getAccessToken() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Jwt jwt) {
            return Optional.of(jwt.getTokenValue());
        }
        return Optional.empty();
    }

}
