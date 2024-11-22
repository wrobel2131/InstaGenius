package com.instagenius.userservice.application;

import com.instagenius.userservice.domain.CreateUser;
import com.instagenius.userservice.domain.CreatedUser;
import com.instagenius.userservice.domain.SyncUpdateUser;

import java.util.UUID;

public interface UserSynchronizationUseCase {
    CreatedUser createUser(CreateUser createUser);
    void updateUser(UUID id, SyncUpdateUser updateUser);
    void deleteUser(UUID id);
}
