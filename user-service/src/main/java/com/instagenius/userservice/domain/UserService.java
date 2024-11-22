package com.instagenius.userservice.domain;

import com.instagenius.userservice.application.UserPersistencePort;
import com.instagenius.userservice.application.UserUseCase;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


public class UserService implements UserUseCase {
    private final UserPersistencePort userPersistencePort;


    public UserService(UserPersistencePort userPersistencePort) {
        this.userPersistencePort = userPersistencePort;
    }

    @Override
    public User findUserById(UUID id) {
        return userPersistencePort.findUserById(id);
    }

    @Transactional
    @Override
    public User updateUser(UUID id, UpdateUser updateUser) {
        User user = userPersistencePort.findUserById(id);
        String updatedEmail = updateUser.email();
        String updatedUsername = updateUser.username();
        String updatedFirstName = updateUser.firstName();
        String updatedLastName = updateUser.lastName();
        if (updatedEmail != null) {
            user.setEmail(updatedEmail);
        }
        if (updatedUsername != null) {
            user.setUsername(updatedUsername);
        }
        if (updatedFirstName != null) {
            user.setFirstName(updatedFirstName);
        }
        if (updatedLastName != null) {
            user.setLastName(updatedLastName);
        }
        return userPersistencePort.saveUser(user);
    }
}
