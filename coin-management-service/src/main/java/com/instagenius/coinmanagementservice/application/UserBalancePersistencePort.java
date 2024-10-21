package com.instagenius.coinmanagementservice.application;

import com.instagenius.coinmanagementservice.domain.UserBalance;

import java.util.UUID;

public interface UserBalancePersistencePort {
    UserBalance findUserBalanceByUserId(UUID userId);
    UserBalance save(UserBalance userBalance);
    void deleteUserBalanceByUserId(UUID userId);
}
