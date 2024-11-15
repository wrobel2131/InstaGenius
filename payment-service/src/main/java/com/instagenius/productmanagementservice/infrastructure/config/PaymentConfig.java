package com.instagenius.productmanagementservice.infrastructure.config;

import com.instagenius.productmanagementservice.application.PaymentPersistencePort;
import com.instagenius.productmanagementservice.application.PaymentUseCase;
import com.instagenius.productmanagementservice.domain.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
class PaymentConfig {
    private final PaymentPersistencePort paymentPersistencePort;

    @Bean
    PaymentUseCase paymentUseCase() {
        return new PaymentService(paymentPersistencePort);
    }
}
