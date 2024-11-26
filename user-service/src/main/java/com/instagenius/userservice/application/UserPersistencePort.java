package com.instagenius.userservice.application;

import com.instagenius.userservice.domain.User;

import java.util.UUID;

public interface UserPersistencePort {
    User saveUser(User user);
    void deleteUser(UUID id);
    User findUserById(UUID id);
}
