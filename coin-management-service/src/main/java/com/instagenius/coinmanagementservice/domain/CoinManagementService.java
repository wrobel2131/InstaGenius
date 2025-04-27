package com.instagenius.coinmanagementservice.domain;

import com.instagenius.coinmanagementservice.application.CoinManagementUseCase;
import com.instagenius.coinmanagementservice.application.CoinReservationPersistencePort;
import com.instagenius.coinmanagementservice.application.CoinTransactionPersistencePort;
import com.instagenius.coinmanagementservice.application.UserBalancePersistencePort;
import com.instagenius.coinmanagementservice.infrastructure.exception.CoinReservationNotFoundException;
import com.instagenius.coinmanagementservice.infrastructure.exception.InsufficientBalanceException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Slf4j
public class CoinManagementService implements CoinManagementUseCase {
    private final CoinTransactionPersistencePort coinTransactionPersistencePort;
    private final UserBalancePersistencePort userBalancePersistencePort;
    private final CoinReservationPersistencePort coinReservationPersistencePort;
    private static final String INSUFFICIENT_BALANCE_ERROR_MESSAGE = "Insufficient balance";

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
        log.debug("Getting balance for user {}", userId);
        return userBalancePersistencePort.findUserBalanceByUserId(userId);
    }

    @Transactional
    @Override
    public void deleteBalance(UUID userId) {
        log.debug("Deleting balance for user {}", userId);
        userBalancePersistencePort.deleteUserBalanceByUserId(userId);
    }

    @Transactional
    @Override
    public UserBalance createBalance(UUID userId, int initialBalance) {
        log.debug("Creating balance for user {} with initial balance {}", userId, initialBalance);
        return userBalancePersistencePort.save(
                new UserBalance(null, userId, new Balance(initialBalance), new Balance(0), null, null, 0));
    }

    @Transactional
    @Override
    public List<CoinTransaction> getCoinTransactions(UUID userId) {
        log.debug("Getting coin-transactions for user {}", userId);
        return coinTransactionPersistencePort.findCoinTransactionsByUserId(userId);
    }

    @Transactional
    @Override
    public CoinReservation reserveCoins(UUID userId, int amount, UUID operationId) {
        log.debug("Reserving coin-transactions for user {}", userId);
        try {
            return coinReservationPersistencePort.findCoinReservationByOperationId(operationId);
        } catch (CoinReservationNotFoundException e) {
            log.debug("Reservation with operation id {} not found", operationId);
            UserBalance userBalance = userBalancePersistencePort.findUserBalanceByUserId(userId);
            if (userBalance.getAvailableBalance().balance() < amount) {
                throw new InsufficientBalanceException(INSUFFICIENT_BALANCE_ERROR_MESSAGE);
            }
            log.debug("Reserving coins");
            userBalance.setAvailableBalance(new Balance(userBalance.getAvailableBalance().balance() - amount));
            userBalance.setReservedBalance(new Balance(userBalance.getReservedBalance().balance() + amount));
            userBalancePersistencePort.save(userBalance);

            log.debug("Creating coin reservation");
            CoinReservation coinReservation = new CoinReservation(null, userId, new CoinAmount(amount), operationId,
                                                                  ReservationStatus.PENDING, null, Instant.now(), null,
                                                                  0);
            return coinReservationPersistencePort.save(coinReservation);
        }
    }

    @Transactional
    @Override
    public void completeReservation(UUID userId, UUID reservationId) {
        log.debug("Completing reservation with id {}", reservationId);
        CoinReservation coinReservation = coinReservationPersistencePort.findCoinReservationByIdAndUserId(reservationId,
                                                                                                          userId);

        if (!coinReservation.getStatus().equals(ReservationStatus.PENDING)) {
            log.debug("Reservation with id {} is not pending.", reservationId);
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
        log.debug("Cancelling reservation with id {}", reservationId);
        CoinReservation coinReservation = coinReservationPersistencePort.findCoinReservationByIdAndUserId(reservationId,
                                                                                                          userId);

        if (!coinReservation.getStatus().equals(ReservationStatus.PENDING)) {
            log.debug("Reservation with id {} is not pending.", reservationId);
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
        log.debug("Adding coins to user {}", userId);
        UserBalance userBalance = userBalancePersistencePort.findUserBalanceByUserId(userId);

        userBalance.setAvailableBalance(new Balance(userBalance.getAvailableBalance().balance() + amount));
        userBalancePersistencePort.save(userBalance);

        recordCoinTransaction(userId, amount, type);
    }

    private void recordCoinTransaction(UUID userId, int amount, TransactionType type) {
        CoinTransaction coinTransaction = new CoinTransaction(null, userId, new CoinAmount(amount), type, null, 0);
        coinTransactionPersistencePort.save(coinTransaction);
        log.debug("Added coin-transaction {}", coinTransaction);
    }
}
