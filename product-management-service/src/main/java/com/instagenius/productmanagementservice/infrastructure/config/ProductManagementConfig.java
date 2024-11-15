package com.instagenius.productmanagementservice.infrastructure.config;

import com.instagenius.productmanagementservice.application.FileStoragePort;
import com.instagenius.productmanagementservice.application.PaymentGatewayResourcePort;
import com.instagenius.productmanagementservice.application.ProductPersistencePort;
import com.instagenius.productmanagementservice.application.ProductManagementUseCase;
import com.instagenius.productmanagementservice.domain.ProductManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
class ProductManagementConfig {
    private final ProductPersistencePort productPersistencePort;
    private final PaymentGatewayResourcePort paymentGatewayResourcePort;
    private final FileStoragePort fileStoragePort;

    @Bean
    ProductManagementUseCase paymentUseCase() {
        return new ProductManagementService(productPersistencePort, paymentGatewayResourcePort, fileStoragePort);
    }
}
