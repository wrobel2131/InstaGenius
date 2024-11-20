package com.instagenius.user.utils;

import com.instagenius.user.dto.UserDto;
import lombok.experimental.UtilityClass;
import org.keycloak.events.Event;
import org.keycloak.events.EventType;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

import java.util.Map;

@UtilityClass
public class EventHandler {

    public void handleRegisterEvent(Event event, KeycloakSession keycloakSession) {
        EventType eventType = event.getType();

        if(eventType.equals(EventType.REGISTER)) {
            String userId = event.getUserId();
            String realmId = event.getRealmId();
            UserDto user = getUser(keycloakSession, userId, realmId);

            //TODO perform API call to userService to create user

        }
    }

    public void handleDeleteAccountEvent(Event event, KeycloakSession keycloakSession) {
        EventType eventType = event.getType();
        if(eventType.equals(EventType.DELETE_ACCOUNT)) {
            String userId = event.getUserId();
            String realmId = event.getRealmId();

            //TODO perform API call to userService to delete user with given id and realmid
        }
    }


    private UserDto getUser(KeycloakSession keycloakSession, String userId, String realmId) {
        RealmModel realm = keycloakSession.realms().getRealm(realmId);
        UserModel user = keycloakSession.users().getUserById(realm, userId);
        return new UserDto(user.getId(), user.getEmail(), user.getFirstName(), user.getLastName(),
                user.isEmailVerified(), user.isEnabled(), user.getCreatedTimestamp());
    }
}
