package com.instagenius.productmanagementservice.domain;

import com.instagenius.productmanagementservice.application.ProductPersistencePort;
import com.instagenius.productmanagementservice.application.ProductManagementUseCase;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ProductManagementService implements ProductManagementUseCase {
    private final ProductPersistencePort productPersistencePort;

    public ProductManagementService(ProductPersistencePort productPersistencePort) {
        this.productPersistencePort = productPersistencePort;
    }

    @Override
    public Product createProduct(String name, String description, ProductType type, Price price,
                                 Map<String, Object> attributes) {
        return null;
    }

    @Override
    public Product updateProduct(UUID id, String name, String description, ProductType type, Price price,
                                 Map<String, Object> attributes, boolean isActive) {
        return null;
    }

    @Override
    public void deleteProduct(UUID id) {

    }

    @Override
    public Product getProductById(UUID id) {
        return null;
    }

    @Override
    public List<Product> getProducts() {
        return List.of();
    }

    @Override
    public List<Product> getProductsByType(ProductType type) {
        return List.of();
    }
}
