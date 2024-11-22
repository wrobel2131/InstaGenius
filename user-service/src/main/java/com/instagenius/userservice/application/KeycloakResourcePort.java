package com.instagenius.userservice.application;

import com.instagenius.userservice.domain.UpdateUser;

import java.util.UUID;

public interface KeycloakResourcePort {
    void updateUser(UUID id, String kcUserId, String kcRealmId,UpdateUser updateUser);
}
