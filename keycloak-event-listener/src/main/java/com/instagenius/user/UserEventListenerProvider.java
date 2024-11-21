package com.instagenius.user;

import com.instagenius.user.utils.EventHandler;
import lombok.RequiredArgsConstructor;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.models.KeycloakSession;

import java.util.logging.Logger;

@RequiredArgsConstructor
public class UserEventListenerProvider implements EventListenerProvider {
    private static final Logger logger = Logger.getLogger(UserEventListenerProvider.class.getName());
    private final KeycloakSession keycloakSession;

    @Override
    public void onEvent(Event event) {
        EventType eventType = event.getType();
        logger.info("Event occurred: " + event.getType());

        switch (eventType) {
            case REGISTER -> {
                logger.info("Register event");
                EventHandler.handleRegisterEvent(event, keycloakSession);
            }
            case DELETE_ACCOUNT -> {
                logger.info("Delete account event");
                EventHandler.handleDeleteAccountEvent(event, keycloakSession);
            }
            case USER_DISABLED_BY_PERMANENT_LOCKOUT -> {
                logger.info("Permanent lockout user disabled event");
                EventHandler.handleUpdateUserRelatedEvent(event, keycloakSession);
            }
            case USER_DISABLED_BY_TEMPORARY_LOCKOUT -> {
                logger.info("Temporary lockout user disabled event");
                EventHandler.handleUpdateUserRelatedEvent(event, keycloakSession);
            }
            case UPDATE_EMAIL -> {
                logger.info("Update email event");
                EventHandler.handleUpdateUserRelatedEvent(event, keycloakSession);
            }
            case UPDATE_PROFILE -> {
                logger.info("Update profile event");
                EventHandler.handleUpdateUserRelatedEvent(event, keycloakSession);
            }
            case VERIFY_EMAIL -> {
                logger.info("Verify email event");
                EventHandler.handleUpdateUserRelatedEvent(event, keycloakSession);
            }
            default -> logger.info("Other event occurred");
        }
    }

    @Override
    public void onEvent(AdminEvent adminEvent, boolean b) {
        // Do nothing
    }

    @Override
    public void close() {
        // Do nothing
    }
}
