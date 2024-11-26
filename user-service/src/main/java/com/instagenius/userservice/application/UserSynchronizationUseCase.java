package com.instagenius.userservice.application;

import com.instagenius.userservice.domain.CreateUser;

import java.util.UUID;

public interface UserSynchronizationUseCase {
    void createUser(CreateUser createUser);
    void deleteUser(UUID id);
}
