package com.instagenius.user;

import org.keycloak.Config;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventListenerProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

public class UserEventListenerProviderFactory implements EventListenerProviderFactory {
    private static final String USER_EVENT_LISTENER_ID = "user-event-listener";

    @Override
    public EventListenerProvider create(KeycloakSession keycloakSession) {
        return new UserEventListenerProvider(keycloakSession);
    }

    @Override
    public void init(Config.Scope scope) {
        // Do nothing
    }

    @Override
    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {
        // Do nothing
    }

    @Override
    public void close() {
        // Do nothing
    }

    @Override
    public String getId() {
        return USER_EVENT_LISTENER_ID;
    }
}
