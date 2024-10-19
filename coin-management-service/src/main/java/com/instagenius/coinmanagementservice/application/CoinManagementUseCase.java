package com.instagenius.coinmanagementservice.application;

import com.instagenius.coinmanagementservice.domain.CoinTransaction;
import com.instagenius.coinmanagementservice.domain.TransactionType;
import com.instagenius.coinmanagementservice.domain.UserBalance;

import java.util.List;
import java.util.UUID;

public interface CoinManagementUseCase {
    UserBalance createBalance(UUID userId, int initialBalance);
    UserBalance getBalance(UUID userId);
    List<CoinTransaction> getCoinTransactions(UUID userId);
    void addCoins(UUID userId, int amount, TransactionType transactionType);
    void deductCoins(UUID userId, int amount);
}
