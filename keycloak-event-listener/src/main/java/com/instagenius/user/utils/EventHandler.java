package com.instagenius.user.utils;

import com.instagenius.user.dto.UpdateUserDto;
import com.instagenius.user.dto.UserDto;
import lombok.experimental.UtilityClass;
import org.keycloak.events.Event;
import org.keycloak.events.EventType;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

import java.util.logging.Logger;

@UtilityClass
public class EventHandler {
    private static final Logger logger = Logger.getLogger(EventHandler.class.getName());

    public void handleRegisterEvent(Event event, KeycloakSession keycloakSession) {
        EventType eventType = event.getType();

        if(eventType.equals(EventType.REGISTER)) {
            String userId = event.getUserId();
            String realmId = event.getRealmId();
            UserDto user = getUser(keycloakSession, userId, realmId);

            logger.info("Adding user");
            ExternalApiUtils.performExternalPOSTApiCall("http://localhost:8080/api/v1/users", user, keycloakSession);
        }
    }

    public void handleDeleteAccountEvent(Event event, KeycloakSession keycloakSession) {
        EventType eventType = event.getType();
        if(eventType.equals(EventType.DELETE_ACCOUNT)) {
            String userId = event.getUserId();
            String realmId = event.getRealmId();

            logger.info("Deleting user");

            ExternalApiUtils.performExternalDELETEApiCall(String.format("http://localhost:8080/api/v1/users/%s/%s", userId,
                    realmId), keycloakSession);
        }
    }

    public void handleUpdateUserRelatedEvent(Event event, KeycloakSession keycloakSession) {
        EventType eventType = event.getType();
        if(eventType.equals(EventType.UPDATE_EMAIL)
                || eventType.equals(EventType.USER_DISABLED_BY_PERMANENT_LOCKOUT)
                || eventType.equals(EventType.USER_DISABLED_BY_TEMPORARY_LOCKOUT)
                || eventType.equals(EventType.UPDATE_PROFILE)
                || eventType.equals(EventType.VERIFY_EMAIL)
        ) {
            String userId = event.getUserId();
            String realmId = event.getRealmId();
            UserDto user = getUser(keycloakSession, userId, realmId);
            UpdateUserDto updateUserDto = UpdateUserDto.builder()
                    .email(user.email())
                    .username(user.username())
                    .firstName(user.firstName())
                    .lastName(user.lastName())
                    .enabled(user.enabled())
                    .emailVerified(user.emailVerified())
                    .build();

            logger.info("Updating user");

            ExternalApiUtils.performExternalPUTApiCall(String.format("http://localhost:8080/api/v1/users/%s/%s", userId,
                    realmId), updateUserDto, keycloakSession);
        }
    }



    private UserDto getUser(KeycloakSession keycloakSession, String userId, String realmId) {
        RealmModel realm = keycloakSession.realms().getRealm(realmId);
        UserModel user = keycloakSession.users().getUserById(realm, userId);
        return new UserDto(user.getId(), realm.getId(), user.getUsername(), user.getEmail(), user.getFirstName(), user.getLastName(),
                user.isEmailVerified(), user.isEnabled(), user.getCreatedTimestamp());
    }
}
