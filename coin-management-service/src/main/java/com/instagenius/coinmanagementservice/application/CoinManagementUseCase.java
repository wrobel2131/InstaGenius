package com.instagenius.coinmanagementservice.application;

import com.instagenius.coinmanagementservice.domain.CoinReservation;
import com.instagenius.coinmanagementservice.domain.CoinTransaction;
import com.instagenius.coinmanagementservice.domain.TransactionType;
import com.instagenius.coinmanagementservice.domain.UserBalance;

import java.util.List;
import java.util.UUID;

public interface CoinManagementUseCase {
    UserBalance createBalance(UUID userId, int initialBalance);
    UserBalance getBalance(UUID userId);
    void deleteBalance(UUID userId);
    List<CoinTransaction> getCoinTransactions(UUID userId);
    CoinReservation reserveCoins(UUID userId, int amount, UUID operationId);
    void completeReservation(UUID userId, Long reservationId);
    void cancelReservation(UUID userId, Long reservationId);
    void addCoins(UUID userId, int amount, TransactionType transactionType);
    void cancelExpiredReservations(UUID userId);
//    void deductCoins(UUID userId, int amount);
}
