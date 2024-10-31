package com.instagenius.coinmanagementservice.domain;

import com.instagenius.coinmanagementservice.application.CoinManagementUseCase;
import com.instagenius.coinmanagementservice.application.CoinReservationPersistencePort;
import com.instagenius.coinmanagementservice.application.CoinTransactionPersistencePort;
import com.instagenius.coinmanagementservice.application.UserBalancePersistencePort;
import com.instagenius.coinmanagementservice.infrastructure.exception.CoinReservationNotFoundException;
import com.instagenius.coinmanagementservice.infrastructure.exception.InsufficientBalanceException;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class CoinManagementService implements CoinManagementUseCase {
    private final CoinTransactionPersistencePort coinTransactionPersistencePort;
    private final UserBalancePersistencePort userBalancePersistencePort;
    private final CoinReservationPersistencePort coinReservationPersistencePort;

    public CoinManagementService(CoinTransactionPersistencePort coinTransactionPersistencePort, UserBalancePersistencePort userBalancePersistencePort, CoinReservationPersistencePort coinReservationPersistencePort) {
        this.coinTransactionPersistencePort = coinTransactionPersistencePort;
        this.userBalancePersistencePort = userBalancePersistencePort;
        this.coinReservationPersistencePort = coinReservationPersistencePort;
    }


    @Override
    public UserBalance getBalance(UUID userId) {
        return userBalancePersistencePort.findUserBalanceByUserId(userId);
    }

    @Transactional
    @Override
    public void deleteBalance(UUID userId) {
        userBalancePersistencePort.deleteUserBalanceByUserId(userId);
    }

    @Transactional
    @Override
    public UserBalance createBalance(UUID userId, int initialBalance) {
        return userBalancePersistencePort.save(new UserBalance(null, userId, new Balance(initialBalance), new Balance(0), null, null));
    }

    @Transactional
    @Override
    public List<CoinTransaction> getCoinTransactions(UUID userId) {
        return coinTransactionPersistencePort.findCoinTransactionsByUserId(userId);
    }

    @Transactional
    @Override
    public CoinReservation reserveCoins(UUID userId, int amount, UUID operationId) {
        try {
            return coinReservationPersistencePort.findCoinReservationByOperationId(operationId);
        } catch (CoinReservationNotFoundException e) {
            UserBalance userBalance = userBalancePersistencePort.findUserBalanceByUserId(userId);
            if (userBalance.getAvailableBalance().balance() < amount) {
                throw new InsufficientBalanceException("Insufficient balance");
            }

            userBalance.setAvailableBalance(new Balance(userBalance.getAvailableBalance().balance() - amount));
            userBalance.setReservedBalance(new Balance(userBalance.getReservedBalance().balance() + amount));
            userBalancePersistencePort.save(userBalance);

            CoinReservation coinReservation = new CoinReservation(null, userId, new CoinAmount(amount), operationId,
                    ReservationStatus.PENDING, null, LocalDateTime.now());
            return coinReservationPersistencePort.save(coinReservation);
        }
    }

    @Transactional
    @Override
    public void completeReservation(UUID userId, Long reservationId) {
        CoinReservation coinReservation = coinReservationPersistencePort.findCoinReservationByIdAndUserId(reservationId, userId);

        if(!coinReservation.getStatus().equals(ReservationStatus.PENDING)) {
            return;
        }

        UserBalance userBalance = userBalancePersistencePort.findUserBalanceByUserId(userId);
        userBalance.setReservedBalance(new Balance(userBalance.getReservedBalance().balance() - coinReservation.getAmount().amount()));
        userBalancePersistencePort.save(userBalance);

        coinReservation.setStatus(ReservationStatus.COMPLETED);
        coinReservationPersistencePort.save(coinReservation);

        recordCointTransaction(userId, coinReservation.getAmount().amount(), TransactionType.SPEND);
    }

    @Transactional
    @Override
    public void cancelReservation(UUID userId, Long reservationId) {
        CoinReservation  coinReservation = coinReservationPersistencePort.findCoinReservationByIdAndUserId(reservationId, userId);

        if(!coinReservation.getStatus().equals(ReservationStatus.PENDING)) {
            return;
        }

        UserBalance userBalance = userBalancePersistencePort.findUserBalanceByUserId(userId);
        userBalance.setAvailableBalance(new Balance(userBalance.getAvailableBalance().balance() + coinReservation.getAmount().amount()));
        userBalance.setReservedBalance(new Balance(userBalance.getReservedBalance().balance() - coinReservation.getAmount().amount()));
        userBalancePersistencePort.save(userBalance);

        coinReservation.setStatus(ReservationStatus.CANCELLED);
        coinReservationPersistencePort.save(coinReservation);
    }

    @Transactional
    @Override
    public void addCoins(UUID userId, int amount, TransactionType type) {
        /* Adding coins to user balance */
        UserBalance userBalance = userBalancePersistencePort.findUserBalanceByUserId(userId);

        userBalance.setAvailableBalance(new Balance(userBalance.getAvailableBalance().balance() + amount));
        userBalancePersistencePort.save(userBalance);

        /* Saving coin transaction */
        recordCointTransaction(userId, amount, type);
    }

    @Override
    @Scheduled(fixedRate = 60000)
    public void cancelExpiredReservations(UUID userId) {
        List<CoinReservation> coinReservations = coinReservationPersistencePort.findCoinReservationsByUserIdAndStatusAndExpiryTimeBefore(userId, ReservationStatus.PENDING, LocalDateTime.now());
        for (CoinReservation coinReservation: coinReservations) {
            cancelReservation(userId, coinReservation.getId());
        }

    }

    

    private void recordCointTransaction(UUID userId, int amount, TransactionType type) {
        CoinTransaction coinTransaction = new CoinTransaction(null, userId, new CoinAmount(amount), type, null);
        coinTransactionPersistencePort.save(coinTransaction);
    }
}
