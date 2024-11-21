package com.instagenius.userservice.infrastructure.adapter;

import com.instagenius.userservice.application.UserPersistencePort;
import com.instagenius.userservice.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserRepository implements UserPersistencePort {
    @Override
    public void createUser(User user) {

    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public void deleteUser(String kcUserId, String kcRealmId) {

    }

    @Override
    public User findUserById(UUID id) {
        return null;
    }
}

interface JpaUserRepository extends JpaRepository<UserEntity, UUID> {

}
