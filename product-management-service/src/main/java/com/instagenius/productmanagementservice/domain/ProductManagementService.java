package com.instagenius.productmanagementservice.domain;

import com.instagenius.productmanagementservice.application.ProductPersistencePort;
import com.instagenius.productmanagementservice.application.ProductManagementUseCase;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductManagementService implements ProductManagementUseCase {
    private final ProductPersistencePort productPersistencePort;

    public ProductManagementService(ProductPersistencePort productPersistencePort) {
        this.productPersistencePort = productPersistencePort;
    }
}
