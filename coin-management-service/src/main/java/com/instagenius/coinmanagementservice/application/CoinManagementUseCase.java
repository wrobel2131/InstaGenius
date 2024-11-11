package com.instagenius.coinmanagementservice.application;

import com.instagenius.coinmanagementservice.domain.*;

import java.util.List;
import java.util.UUID;

public interface CoinManagementUseCase {
    UserBalance createBalance(UUID userId, int initialBalance);
    UserBalance getBalance(UUID userId);
    void deleteBalance(UUID userId);
    List<CoinTransaction> getCoinTransactions(UUID userId);
    CoinReservation reserveCoins(UUID userId, int amount, UUID operationId);
    void completeReservation(UUID userId, UUID reservationId);
    void cancelReservation(UUID userId, UUID reservationId);
    void addCoins(UUID userId, int amount, TransactionType transactionType);
}
