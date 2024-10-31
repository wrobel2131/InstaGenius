package com.instagenius.coinmanagementservice.domain;

import com.instagenius.coinmanagementservice.application.CoinManagementUseCase;
import com.instagenius.coinmanagementservice.application.CoinTransactionPersistencePort;
import com.instagenius.coinmanagementservice.application.UserBalancePersistencePort;
import jakarta.transaction.Transactional;
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
        return userBalancePersistencePort.findUserBalanceByUserId(userId);
    }

    @Transactional
    @Override
    public void deleteBalance(UUID userId) {
        userBalancePersistencePort.deleteUserBalanceByUserId(userId);
    }

    @Override
    public UserBalance createBalance(UUID userId, int initialBalance) {
        return userBalancePersistencePort.save(new UserBalance(null, userId, new Balance(initialBalance), null, null, 0));
    }

    @Override
    public List<CoinTransaction> getCoinTransactions(UUID userId) {
        return coinTransactionPersistencePort.findCoinTransactionsByUserId(userId);
    }

    @Transactional
    @Override
    public void addCoins(UUID userId, int amount, TransactionType type) {
        /* Adding coins to user balance */
        UserBalance userBalance = userBalancePersistencePort.findUserBalanceByUserId(userId);
        System.out.println("userBalance: " + userBalance);
        userBalance.setBalance(new Balance(userBalance.getBalance().balance() + amount));
        userBalancePersistencePort.save(userBalance);

        /* Saving coin transaction */
        CoinTransaction coinTransaction = new CoinTransaction(null, userId, new CoinAmount(amount), type, null);
        coinTransactionPersistencePort.save(coinTransaction);
    }

    @Override
    @Transactional
    public void deductCoins(UUID userId, int amount) {
        /* Deducting coins from user balance */
        UserBalance userBalance = userBalancePersistencePort.findUserBalanceByUserId(userId);
        userBalance.setBalance(new Balance(userBalance.getBalance().balance() - amount));
        userBalancePersistencePort.save(userBalance);

        /* Saving coin transaction */
        CoinTransaction coinTransaction = new CoinTransaction(null, userId, new CoinAmount(amount), TransactionType.SPEND, null);
        coinTransactionPersistencePort.save(coinTransaction);
    }
}
