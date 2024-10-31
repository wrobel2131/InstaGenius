package com.instagenius.coinmanagementservice.infrastructure.adapters;

import com.instagenius.coinmanagementservice.application.UserBalancePersistencePort;
import com.instagenius.coinmanagementservice.domain.UserBalance;
import com.instagenius.coinmanagementservice.infrastructure.exception.UserNotFoundException;
import com.instagenius.coinmanagementservice.infrastructure.mapper.UserBalanceMapper;
import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
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
                        .orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found"))
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
interface JpaUserBalanceRepository extends JpaRepository<UserBalanceEntity, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<UserBalanceEntity> findUserBalanceEntityByUserId(UUID userId);
    void deleteUserBalanceEntityByUserId(UUID userId);
}
