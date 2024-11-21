package com.instagenius.userservice.application;

import com.instagenius.userservice.domain.User;

import java.util.UUID;

public interface UserPersistencePort {
    void createUser(User user);
    void updateUser(User user);
    void deleteUser(String kcUserId, String kcRealmId);
    User findUserById(UUID id);
}
