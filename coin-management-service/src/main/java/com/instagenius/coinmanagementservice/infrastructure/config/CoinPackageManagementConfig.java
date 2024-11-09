package com.instagenius.coinmanagementservice.infrastructure.config;

import com.instagenius.coinmanagementservice.application.CoinPackageManagementUseCase;
import com.instagenius.coinmanagementservice.domain.CoinPackageManagementService;
import com.instagenius.coinmanagementservice.infrastructure.adapter.CoinPackageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
class CoinPackageManagementConfig {
    private final CoinPackageRepository coinPackageRepository;

    @Bean
    CoinPackageManagementUseCase coinPackageManagementUseCase() {
        return new CoinPackageManagementService(coinPackageRepository);
    }
}
