package com.instagenius.coinmanagementservice.infrastructure.adapters;

import com.instagenius.coinmanagementservice.application.CoinPackagePersistencePort;
import com.instagenius.coinmanagementservice.domain.CoinPackage;
import com.instagenius.coinmanagementservice.infrastructure.exception.CoinPackageNotFoundException;
import com.instagenius.coinmanagementservice.infrastructure.mapper.CoinPackageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CoinPackageRepository implements CoinPackagePersistencePort {
    private static final CoinPackageMapper coinPackageMapper = CoinPackageMapper.INSTANCE;
    private final JpaCoinPackageRepository jpaCoinPackageRepository;

    @Override
    public List<CoinPackage> findCoinPackages() {
        return jpaCoinPackageRepository
                .findAll()
                .stream()
                .map(coinPackageMapper::toCoinPackage)
                .toList();
    }

    @Override
    public CoinPackage findCoinPackageById(UUID id) {
        return coinPackageMapper.toCoinPackage(jpaCoinPackageRepository.findCoinPackageEntityById(id).orElseThrow(() -> new CoinPackageNotFoundException("Coin Package not found!")));
    }

    @Override
    public CoinPackage saveCoinPackage(CoinPackage coinPackage) {
        return coinPackageMapper.toCoinPackage(
                jpaCoinPackageRepository.save(
                        coinPackageMapper.toCoinPackageEntity(coinPackage)
                )
        );
    }

    @Override
    public void deleteCoinPackage(UUID id) {
        jpaCoinPackageRepository.deleteCoinPackageEntityById(id);
    }
}

@Repository
interface JpaCoinPackageRepository extends JpaRepository<CoinPackageEntity, UUID> {
    @Query(value = "SELECT c FROM CoinPackageEntity c WHERE c.id = :id")
    Optional<CoinPackageEntity> findCoinPackageEntityById(@Param("id") UUID id);

    @Query(value = "DELETE FROM CoinPackageEntity c WHERE c.id = :id")
    @Modifying
    void deleteCoinPackageEntityById(@Param("id") UUID id);
}
