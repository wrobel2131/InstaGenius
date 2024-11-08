package com.instagenius.coinmanagementservice.application;

import com.instagenius.coinmanagementservice.domain.CoinPackage;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface CoinPackageManagementUseCase {
    List<CoinPackage> getCoinPackages();
    CoinPackage getCoinPackage(UUID packageId);
    void deleteCoinPackage(UUID packageId);
    CoinPackage createCoinPackage(String name, String description, int coinAmount, BigDecimal price, String currency);
    void updateCoinPackage(UUID packageId, String name, String description, Integer coinAmount, BigDecimal price,
                           String currency, Boolean isActive);
}
