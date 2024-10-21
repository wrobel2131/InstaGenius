package com.instagenius.coinmanagementservice.application;

import com.instagenius.coinmanagementservice.domain.CoinTransaction;
import com.instagenius.coinmanagementservice.domain.UserBalance;

import java.util.List;
import java.util.UUID;

public interface CoinTransactionPersistencePort {
    CoinTransaction save(CoinTransaction coinTransaction);
    List<CoinTransaction> findCoinTransactionsByUserId(UUID userId);
}
