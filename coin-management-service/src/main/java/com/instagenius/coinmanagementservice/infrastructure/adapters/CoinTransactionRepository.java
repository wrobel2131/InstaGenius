package com.instagenius.coinmanagementservice.infrastructure.adapters;

import com.instagenius.coinmanagementservice.application.CoinTransactionPersistencePort;
import com.instagenius.coinmanagementservice.domain.CoinTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CoinTransactionRepository implements CoinTransactionPersistencePort {
    private final JpaCoinTransactionRepository coinTransactionRepository;

    @Override
    public CoinTransaction save(CoinTransaction coinTransaction) {
        return null;
    }

    @Override
    public List<CoinTransaction> findCoinTransactionsByUserId(UUID userId) {
        return List.of();
    }

}

@Repository
interface JpaCoinTransactionRepository extends JpaRepository<CoinTransactionEntity, Long> {}


