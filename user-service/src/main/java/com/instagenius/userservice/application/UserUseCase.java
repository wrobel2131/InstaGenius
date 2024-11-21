package com.instagenius.userservice.application;

import com.instagenius.userservice.domain.CreateUser;
import com.instagenius.userservice.domain.UpdateUser;
import com.instagenius.userservice.domain.User;

import java.util.UUID;

public interface UserUseCase {
    User createUser(CreateUser createUser);
    User updateUser(String kcRealmId, String kcUserId, UpdateUser updateUser);
    void deleteUser(String kcRealmId, String kcUserId);
    User getUserById(UUID id);
}
