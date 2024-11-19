package com.instagenius.paymentservice.infrastructure.config;

import com.instagenius.paymentservice.application.*;
import com.instagenius.paymentservice.domain.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
class PaymentConfig {
    private final PaymentPersistencePort paymentPersistencePort;
    private final PaymentGatewayPort paymentGatewayPort;
    private final ProductManagementPort productManagementPort;
    private final AsyncPaymentProcessPort asyncPaymentProcessPort;

    @Bean
    PaymentUseCase paymentUseCase() {
        return new PaymentService(paymentPersistencePort, paymentGatewayPort, productManagementPort, asyncPaymentProcessPort);
    }
}
