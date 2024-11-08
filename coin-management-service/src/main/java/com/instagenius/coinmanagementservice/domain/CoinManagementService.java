package com.instagenius.coinmanagementservice.domain;

import com.instagenius.coinmanagementservice.application.CoinManagementUseCase;
import com.instagenius.coinmanagementservice.application.CoinReservationPersistencePort;
import com.instagenius.coinmanagementservice.application.CoinTransactionPersistencePort;
import com.instagenius.coinmanagementservice.application.UserBalancePersistencePort;
import com.instagenius.coinmanagementservice.infrastructure.exception.CoinReservationNotFoundException;
import com.instagenius.coinmanagementservice.infrastructure.exception.InsufficientBalanceException;
import jakarta.transaction.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class CoinManagementService implements CoinManagementUseCase {
    private final CoinTransactionPersistencePort coinTransactionPersistencePort;
    private final UserBalancePersistencePort userBalancePersistencePort;
    private final CoinReservationPersistencePort coinReservationPersistencePort;

    public CoinManagementService(
            CoinTransactionPersistencePort coinTransactionPersistencePort,
            UserBalancePersistencePort userBalancePersistencePort,
            CoinReservationPersistencePort coinReservationPersistencePort) {
        this.coinTransactionPersistencePort = coinTransactionPersistencePort;
        this.userBalancePersistencePort = userBalancePersistencePort;
        this.coinReservationPersistencePort = coinReservationPersistencePort;
    }

    @Transactional
    @Override
    public UserBalance getBalance(UUID userId) {
        System.out.println("Getting balance for user " + userId);
        return userBalancePersistencePort.findUserBalanceByUserId(userId);
    }

    @Transactional
    @Override
    public void deleteBalance(UUID userId) {
        System.out.println("Deleting balance for user " + userId);
        userBalancePersistencePort.deleteUserBalanceByUserId(userId);
    }

    @Transactional
    @Override
    public UserBalance createBalance(UUID userId, int initialBalance) {
        System.out.println("Creating balance for user " + userId + " with initial balance " + initialBalance);
        return userBalancePersistencePort.save(
                new UserBalance(null, userId, new Balance(initialBalance), new Balance(0), null, null, 0));
    }

    @Transactional
    @Override
    public List<CoinTransaction> getCoinTransactions(UUID userId) {
        System.out.println("Getting coin-transactions for user " + userId);
        return coinTransactionPersistencePort.findCoinTransactionsByUserId(userId);
    }

    @Transactional
    @Override
    public CoinReservation reserveCoins(UUID userId, int amount, UUID operationId) {
        System.out.println("Reserving coin-transactions for user " + userId);
        try {
            return coinReservationPersistencePort.findCoinReservationByOperationId(operationId);
        } catch (CoinReservationNotFoundException e) {
            System.out.println("Reservation with operation id " + operationId + " not found");
            UserBalance userBalance = userBalancePersistencePort.findUserBalanceByUserId(userId);
            if (userBalance.getAvailableBalance().balance() < amount) {
                throw new InsufficientBalanceException("Insufficient balance");
            }
            System.out.println("Reserving coins");
            userBalance.setAvailableBalance(new Balance(userBalance.getAvailableBalance().balance() - amount));
            userBalance.setReservedBalance(new Balance(userBalance.getReservedBalance().balance() + amount));
            userBalancePersistencePort.save(userBalance);

            System.out.println("Creating coin reservation");
            CoinReservation coinReservation = new CoinReservation(null, userId, new CoinAmount(amount), operationId,
                                                                  ReservationStatus.PENDING, null, Instant.now(), null,
                                                                  0);
            return coinReservationPersistencePort.save(coinReservation);
        }
    }

    @Transactional
    @Override
    public void completeReservation(UUID userId, UUID reservationId) {
        System.out.println("Completing reservation with id " + reservationId);
        CoinReservation coinReservation = coinReservationPersistencePort.findCoinReservationByIdAndUserId(reservationId,
                                                                                                          userId);

        if (!coinReservation.getStatus().equals(ReservationStatus.PENDING)) {
            System.out.println("Reservation with id " + reservationId + " is not pending.");
            return;
        }

        UserBalance userBalance = userBalancePersistencePort.findUserBalanceByUserId(userId);
        userBalance.setReservedBalance(
                new Balance(userBalance.getReservedBalance().balance() - coinReservation.getAmount().amount()));
        userBalancePersistencePort.save(userBalance);

        coinReservation.setStatus(ReservationStatus.COMPLETED);
        coinReservationPersistencePort.save(coinReservation);

        recordCoinTransaction(userId, coinReservation.getAmount().amount(), TransactionType.SPEND);
    }

    @Transactional
    @Override
    public void cancelReservation(UUID userId, UUID reservationId) {
        System.out.println("Cancelling reservation with id " + reservationId);
        CoinReservation coinReservation = coinReservationPersistencePort.findCoinReservationByIdAndUserId(reservationId,
                                                                                                          userId);

        if (!coinReservation.getStatus().equals(ReservationStatus.PENDING)) {
            System.out.println("Reservation with id " + reservationId + " is not pending.");
            return;
        }

        UserBalance userBalance = userBalancePersistencePort.findUserBalanceByUserId(userId);
        userBalance.setAvailableBalance(
                new Balance(userBalance.getAvailableBalance().balance() + coinReservation.getAmount().amount()));
        userBalance.setReservedBalance(
                new Balance(userBalance.getReservedBalance().balance() - coinReservation.getAmount().amount()));
        userBalancePersistencePort.save(userBalance);

        coinReservation.setStatus(ReservationStatus.CANCELLED);
        coinReservationPersistencePort.save(coinReservation);

    }

    @Transactional
    @Override
    public void addCoins(UUID userId, int amount, TransactionType type) {
        System.out.println("Adding coins to user " + userId);
        UserBalance userBalance = userBalancePersistencePort.findUserBalanceByUserId(userId);

        userBalance.setAvailableBalance(new Balance(userBalance.getAvailableBalance().balance() + amount));
        userBalancePersistencePort.save(userBalance);

        recordCoinTransaction(userId, amount, type);
    }

    private void recordCoinTransaction(UUID userId, int amount, TransactionType type) {
        CoinTransaction coinTransaction = new CoinTransaction(null, userId, new CoinAmount(amount), type, null, 0);
        coinTransactionPersistencePort.save(coinTransaction);
        System.out.println("Added coin-transaction " + coinTransaction);
    }
}
