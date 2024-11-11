package com.instagenius.paymentservice.infrastructure.config;

import com.instagenius.paymentservice.application.PaymentPersistencePort;
import com.instagenius.paymentservice.application.PaymentUseCase;
import com.instagenius.paymentservice.domain.PaymentService;
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
