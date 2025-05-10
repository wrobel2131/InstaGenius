package com.instagenius.user;

import com.instagenius.user.utils.EventHandler;
import lombok.RequiredArgsConstructor;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.events.admin.OperationType;
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
            case UPDATE_PROFILE -> {
                logger.info("Update profile event");
                EventHandler.handleUpdateUserRelatedEvent(event, keycloakSession);
            }
            default -> logger.info("Other event occurred");
        }
    }

    @Override
    public void onEvent(AdminEvent adminEvent, boolean b) {
        logger.info("Admin Event occurred:" + adminEvent.getResourceTypeAsString());
        logger.info("Admin event operation type: " + adminEvent.getOperationType());
        OperationType operationType = adminEvent.getOperationType();
        switch (operationType) {
            case UPDATE -> {
                logger.info("Update operation");
                EventHandler.handleUpdateAdminRelatedEvent(adminEvent, keycloakSession);
            }
            case DELETE -> {
                //TODO check if it's instagenius user so check if he has role from instagenius-frontend USER
                logger.info("Delete operation");
                EventHandler.handleDeleteAccountAdminRelatedEvent(adminEvent, keycloakSession);
            }
            default -> logger.info("Other operation occurred");
        }

    }

    @Override
    public void close() {
        // Do nothing
    }
}
