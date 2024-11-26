package com.instagenius.userservice.domain;

import com.instagenius.userservice.application.UserPersistencePort;
import com.instagenius.userservice.application.UserSynchronizationUseCase;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public class UserSynchronizationService implements UserSynchronizationUseCase {
    private final UserPersistencePort userPersistencePort;

    public UserSynchronizationService(UserPersistencePort userPersistencePort) {
        this.userPersistencePort = userPersistencePort;
    }

    @Transactional
    @Override
    public void createUser(CreateUser createUser) {
        User user = new User(createUser.id(), createUser.username(), createUser.email(),
                createUser.firstName(), createUser.lastName(), createUser.emailVerified(), createUser.enabled(), createUser.createdAt());
        userPersistencePort.saveUser(user);
    }

    @Transactional
    @Override
    public void deleteUser(UUID id) {
        userPersistencePort.deleteUser(id);
    }
}
