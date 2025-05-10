package com.instagenius.user.utils;

import com.instagenius.user.dto.UpdateUserDto;
import com.instagenius.user.dto.UserDto;
import lombok.experimental.UtilityClass;
import org.keycloak.events.Event;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.events.admin.ResourceType;
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
            ExternalApiUtils.performExternalPOSTApiCall("http://host.docker.internal:8200/api/v1/sync/users", user,
                                                        keycloakSession);
        }
    }
    public void handleDeleteAccountAdminRelatedEvent(AdminEvent event, KeycloakSession keycloakSession) {

        if(ResourceType.USER.equals(event.getResourceType())) {

            logger.info("Deleting user");
            String userId = event.getResourcePath().replace("users/", "");

            logger.info("Deleting user with id: " + userId);
            ExternalApiUtils.performExternalDELETEApiCall("http://host.docker.internal:8200/api/v1/sync/users/" + userId,
                                                          keycloakSession);
        }
    }

    public void handleUpdateAdminRelatedEvent(AdminEvent event, KeycloakSession keycloakSession) {

        if(ResourceType.USER.equals(event.getResourceType())) {
            logger.info("Updating the user with admin event");
            String userId = event.getResourcePath().replace("users/", "");
            logger.info("Updating user with id: " + userId);
            String realmId = event.getRealmId();
            UserDto user = getUser(keycloakSession, userId, realmId);
            UpdateUserDto updateUserDto = new UpdateUserDto(user.username(),
                                                            user.email(),
                                                            user.firstName(),
                                                            user.lastName(),
                                                            user.emailVerified(),
                                                            user.enabled());

            logger.info("Updating user");

            ExternalApiUtils.performExternalPUTApiCall("http://host.docker.internal:8200/api/v1/sync/users/" + userId,
                                                       updateUserDto,
                                                       keycloakSession);
        } else {
            logger.info("Admin event not updating the user");
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
            UpdateUserDto updateUserDto = new UpdateUserDto(user.username(),
                                                            user.email(),
                                                            user.firstName(),
                                                            user.lastName(),
                                                            user.emailVerified(),
                                                            user.enabled());

            logger.info("Updating user");

            ExternalApiUtils.performExternalPUTApiCall("http://host.docker.internal:8200/api/v1/sync/users/"+userId,
                                                       updateUserDto,
                                                        keycloakSession);
        }
    }

    private UserDto getUser(KeycloakSession keycloakSession, String userId, String realmId) {
        RealmModel realm = keycloakSession.realms().getRealm(realmId);
        UserModel user = keycloakSession.users().getUserById(realm, userId);
        return new UserDto(user.getId(), user.getUsername(), user.getEmail(), user.getFirstName(), user.getLastName(),
                user.isEmailVerified(), user.isEnabled(), user.getCreatedTimestamp());
    }
}
