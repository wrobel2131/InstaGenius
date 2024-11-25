package com.instagenius.userservice.application;

import com.instagenius.userservice.domain.UpdateUser;

import java.util.UUID;

public interface KeycloakAccountManagementPort {
    void updateUser(UUID id, UpdateUser updateUser);
}
