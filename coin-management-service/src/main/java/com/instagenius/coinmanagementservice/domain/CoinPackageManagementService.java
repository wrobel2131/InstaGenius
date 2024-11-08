package com.instagenius.coinmanagementservice.domain;


import com.instagenius.coinmanagementservice.application.CoinPackageManagementUseCase;
import com.instagenius.coinmanagementservice.infrastructure.adapters.CoinPackageRepository;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CoinPackageManagementService implements CoinPackageManagementUseCase {
    private final CoinPackageRepository coinPackageRepository;

    public CoinPackageManagementService(CoinPackageRepository coinPackageRepository) {
        this.coinPackageRepository = coinPackageRepository;
    }

    @Override
    public List<CoinPackage> getCoinPackages() {
        return coinPackageRepository.findCoinPackages();
    }

    @Override
    public CoinPackage getCoinPackage(UUID packageId) {
        return coinPackageRepository.findCoinPackageById(packageId);
    }

    @Transactional
    @Override
    public void deleteCoinPackage(UUID packageId) {
        coinPackageRepository.deleteCoinPackage(packageId);
    }

    @Transactional
    @Override
    public CoinPackage createCoinPackage(String name, String description, int coinAmount, BigDecimal price, String currency) {
        CoinPackage newCoinPackage = new CoinPackage(null, name, description, new CoinAmount(coinAmount),
                                                     new Price(price, currency), null, null, true, 0);
        return coinPackageRepository.saveCoinPackage(newCoinPackage);
    }

    @Transactional
    @Override
    public void updateCoinPackage(UUID packageId, String name, String description, Integer coinAmount, BigDecimal price,
                                  String currency, Boolean isActive) {

        CoinPackage coinPackage = coinPackageRepository.findCoinPackageById(packageId);
        if (name != null) {
            coinPackage.setName(name);
        }
        if (description != null) {
            coinPackage.setDescription(description);
        }

        if (coinAmount != null) {
            coinPackage.setCoinAmount(new CoinAmount(coinAmount));
        }

        BigDecimal newPrice = Optional.ofNullable(price).orElse(coinPackage.getPrice().price());
        String newCurrency = Optional.ofNullable(currency).orElse(coinPackage.getPrice().currency());

        coinPackage.setPrice(new Price(newPrice, newCurrency));

        if (isActive != null) {
            coinPackage.setActive(isActive);
        }
        coinPackageRepository.saveCoinPackage(coinPackage);
    }

}
