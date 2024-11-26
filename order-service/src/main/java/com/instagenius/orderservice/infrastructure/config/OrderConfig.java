package com.instagenius.orderservice.infrastructure.config;

import com.instagenius.orderservice.application.*;
import com.instagenius.orderservice.domain.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
class OrderConfig {
    private final OrderPersistencePort orderPersistencePort;
    private final PaymentPort paymentPort;
    private final ProductManagementPort productManagementPort;
    private final OrderCompletionHandlerFactory orderCompletionHandlerFactory;
    private final OrderConfirmationProducerPort orderConfirmationProducerPort;

    @Bean
    OrderUseCase orderUseCase() {
        return new OrderService(orderPersistencePort, productManagementPort, paymentPort, orderCompletionHandlerFactory,
                                orderConfirmationProducerPort);
    }
}
