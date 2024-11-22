package com.instagenius.userservice.infrastructure.adapter;

import com.instagenius.userservice.application.UserPersistencePort;
import com.instagenius.userservice.domain.User;
import com.instagenius.userservice.infrastructure.exception.UserNotFoundException;
import com.instagenius.userservice.infrastructure.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserRepository implements UserPersistencePort {
    private static final UserMapper userMapper = UserMapper.INSTANCE;
    private final JpaUserRepository jpaUserRepository;

    @Override
    public User saveUser(User user) {
        return userMapper.toUser(
          jpaUserRepository.save(
                  userMapper.toUserEntity(user)
          )
        );
    }

    @Override
    public void deleteUser(UUID id) {
        jpaUserRepository.deleteUserEntityById(id);
    }


    @Override
    public User findUserById(UUID id) {
        return userMapper.toUser(
                jpaUserRepository.findUserEntityById(id)
                        .orElseThrow(() -> new UserNotFoundException("User not found!"))
        );
    }
}

@Repository
interface JpaUserRepository extends JpaRepository<UserEntity, UUID> {

    @Query(value = "SELECT u FROM UserEntity u WHERE u.id = :id")
    Optional<UserEntity> findUserEntityById(@Param("id") UUID id);

    @Modifying
    @Query(value = "DELETE FROM UserEntity u WHERE u.id = :id")
    void deleteUserEntityById(@Param("id") UUID id);

}
