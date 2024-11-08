package com.instagenius.coinmanagementservice.infrastructure.adapters;

import com.instagenius.coinmanagementservice.application.UserBalancePersistencePort;
import com.instagenius.coinmanagementservice.domain.UserBalance;
import com.instagenius.coinmanagementservice.infrastructure.exception.UserNotFoundException;
import com.instagenius.coinmanagementservice.infrastructure.mapper.UserBalanceMapper;
import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserBalanceRepository implements UserBalancePersistencePort {
    private final JpaUserBalanceRepository jpaUserBalanceRepository;
    private static final UserBalanceMapper userBalanceMapper = UserBalanceMapper.INSTANCE;

    @Override
    public UserBalance findUserBalanceByUserId(UUID userId) {
        return userBalanceMapper.toUserBalance(
                jpaUserBalanceRepository.findUserBalanceEntityByUserId(userId)
                        .orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found!"))
        );
    }

    @Override
    public UserBalance save(UserBalance userBalance) {
        return userBalanceMapper.toUserBalance(
                jpaUserBalanceRepository.save(userBalanceMapper.toUserBalanceEntity(userBalance))
        );
    }

    @Override
    public void deleteUserBalanceByUserId(UUID userId) {
        jpaUserBalanceRepository.deleteUserBalanceEntityByUserId(userId);
    }
}

@Repository
interface JpaUserBalanceRepository extends JpaRepository<UserBalanceEntity, UUID> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "SELECT b FROM UserBalanceEntity b WHERE b.userId = :userId")
    Optional<UserBalanceEntity> findUserBalanceEntityByUserId(@Param("userId") UUID userId);

    @Query(value = "DELETE FROM UserBalanceEntity u WHERE u.userId = :userId")
    @Modifying
    void deleteUserBalanceEntityByUserId(@Param("userId") UUID userId);
}
