package com.instagenius.coinmanagementservice.infrastructure.config;

import com.instagenius.coinmanagementservice.application.CoinManagementUseCase;
import com.instagenius.coinmanagementservice.application.CoinTransactionPersistencePort;
import com.instagenius.coinmanagementservice.application.UserBalancePersistencePort;
import com.instagenius.coinmanagementservice.domain.CoinManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class CoinManagementConfig {
    private final CoinTransactionPersistencePort coinTransactionPersistencePort;
    private final UserBalancePersistencePort userBalancePersistencePort;

    @Bean
    CoinManagementUseCase coinManagementUseCase() {
        return new CoinManagementService(coinTransactionPersistencePort, userBalancePersistencePort);
    }
}
