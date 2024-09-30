package com.instagenius.coinmanagementservice.domain;

import com.instagenius.coinmanagementservice.application.CoinManagementUseCase;
import com.instagenius.coinmanagementservice.application.CoinTransactionPersistencePort;
import com.instagenius.coinmanagementservice.application.UserBalancePersistencePort;

import java.util.List;
import java.util.UUID;

public class CoinManagementService implements CoinManagementUseCase {
    private final CoinTransactionPersistencePort coinTransactionPersistencePort;
    private final UserBalancePersistencePort userBalancePersistencePort;

    public CoinManagementService(CoinTransactionPersistencePort coinTransactionPersistencePort, UserBalancePersistencePort userBalancePersistencePort) {
        this.coinTransactionPersistencePort = coinTransactionPersistencePort;
        this.userBalancePersistencePort = userBalancePersistencePort;
    }

    @Override
    public UserBalance getBalance(UUID userId) {
        return null;
    }

    @Override
    public List<CoinTransaction> getCoinTransactions(UUID userId) {
        return List.of();
    }

    @Override
    public void addCoins(UUID userId, int amount) {

    }

    @Override
    public void deductCoins(UUID userId, int amount) {

    }
}
