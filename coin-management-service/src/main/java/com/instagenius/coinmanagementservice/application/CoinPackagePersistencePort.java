package com.instagenius.coinmanagementservice.application;

import com.instagenius.coinmanagementservice.domain.CoinPackage;

import java.util.List;
import java.util.UUID;

public interface CoinPackagePersistencePort {
    List<CoinPackage> findCoinPackages();
    CoinPackage findCoinPackageById(UUID id);
    CoinPackage saveCoinPackage(CoinPackage coinPackage);
    void deleteCoinPackage(UUID id);
}
