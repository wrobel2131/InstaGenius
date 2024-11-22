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
    public CreatedUser createUser(CreateUser createUser) {
        User user = new User(null, createUser.id(), createUser.realmId(), createUser.username(), createUser.email(),
                createUser.firstName(), createUser.lastName(), createUser.emailVerified(), createUser.enabled(), createUser.createdAt());
        User createdUser = userPersistencePort.saveUser(user);

        return new CreatedUser(createdUser.getId(), createdUser.getKcUserId(), createdUser.getKcRealmId());
    }

    @Transactional
    @Override
    public void updateUser(UUID id, SyncUpdateUser updateUser) {
        User user = userPersistencePort.findUserById(id);
        String updatedEmail = updateUser.email();
        String updatedUsername = updateUser.username();
        String updatedFirstName = updateUser.firstName();
        String updatedLastName = updateUser.lastName();
        Boolean updatedEmailVerified = updateUser.emailVerified();
        Boolean updatedEnabled = updateUser.enabled();
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
        if (updatedEmailVerified != null) {
            user.setEmailVerified(updatedEmailVerified);
        }
        if (updatedEnabled != null) {
            user.setEnabled(updatedEnabled);
        }
        userPersistencePort.saveUser(user);
    }

    @Transactional
    @Override
    public void deleteUser(UUID id) {
        userPersistencePort.deleteUser(id);
    }
}
