package com.instagenius.userservice.domain;

import com.instagenius.userservice.application.UserPersistencePort;
import com.instagenius.userservice.application.UserUseCase;

import java.util.UUID;

public class UserService implements UserUseCase {
    private final UserPersistencePort userPersistencePort;


    public UserService(UserPersistencePort userPersistencePort) {
        this.userPersistencePort = userPersistencePort;
    }

    @Override
    public User createUser(CreateUser createUser) {
        return null;
    }

    @Override
    public User updateUser(String kcRealmId, String kcUserId, UpdateUser updateUser) {
        return null;
    }

    @Override
    public void deleteUser(String kcRealmId, String kcUserId,) {

    }

    @Override
    public User getUserById(UUID id) {
        return null;
    }
}
