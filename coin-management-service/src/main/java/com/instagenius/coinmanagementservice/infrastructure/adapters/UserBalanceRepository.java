package com.instagenius.coinmanagementservice.infrastructure.adapters;

import com.instagenius.coinmanagementservice.application.UserBalancePersistencePort;
import com.instagenius.coinmanagementservice.domain.UserBalance;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserBalanceRepository implements UserBalancePersistencePort {
    private final JpaUserBalanceRepository jpaUserBalanceRepository;

    @Override
    public UserBalance findUserBalanceByUserId(UUID userId) {
        return null;
    }

    @Override
    public UserBalance save(UserBalance userBalance) {
        return null;
    }
}

@Repository
interface JpaUserBalanceRepository extends JpaRepository<UserBalanceEntity, Long> {}
