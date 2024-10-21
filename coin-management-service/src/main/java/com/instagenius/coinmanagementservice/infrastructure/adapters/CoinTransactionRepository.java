package com.instagenius.coinmanagementservice.infrastructure.adapters;

import com.instagenius.coinmanagementservice.application.CoinTransactionPersistencePort;
import com.instagenius.coinmanagementservice.domain.CoinTransaction;
import com.instagenius.coinmanagementservice.infrastructure.mapper.CoinTransactionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CoinTransactionRepository implements CoinTransactionPersistencePort {
    private final JpaCoinTransactionRepository jpaCoinTransactionRepository;
    private static final CoinTransactionMapper coinTransactionMapper = CoinTransactionMapper.INSTANCE;

    @Override
    public CoinTransaction save(CoinTransaction coinTransaction) {
        return coinTransactionMapper.toCoinTransaction(
                jpaCoinTransactionRepository.save(
                        coinTransactionMapper.toCoinTransactionEntity(coinTransaction)
                )
        );
    }

    @Override
    public List<CoinTransaction> findCoinTransactionsByUserId(UUID userId) {
        return jpaCoinTransactionRepository
                .findCoinTransactionEntitiesByUserId(userId)
                .stream()
                .map(coinTransactionMapper::toCoinTransaction)
                .toList();
    }

}

@Repository
interface JpaCoinTransactionRepository extends JpaRepository<CoinTransactionEntity, Long> {
    List<CoinTransactionEntity> findCoinTransactionEntitiesByUserId(UUID userId);
}


