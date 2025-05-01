package com.instagenius.userservice.application;

import com.instagenius.userservice.domain.CreateUser;
import com.instagenius.userservice.domain.SyncUpdateUser;
import com.instagenius.userservice.domain.UpdateUser;

import java.util.UUID;

public interface UserSynchronizationUseCase {
    void createUser(CreateUser createUser);
    void updateUser(UUID id, SyncUpdateUser syncUpdateUser);
    void deleteUser(UUID id);
}
