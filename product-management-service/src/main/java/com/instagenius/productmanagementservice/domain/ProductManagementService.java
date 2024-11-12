package com.instagenius.productmanagementservice.domain;

import com.instagenius.productmanagementservice.application.ProductPersistencePort;
import com.instagenius.productmanagementservice.application.ProductManagementUseCase;
import jakarta.transaction.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ProductManagementService implements ProductManagementUseCase {
    private final ProductPersistencePort productPersistencePort;

    public ProductManagementService(ProductPersistencePort productPersistencePort) {
        this.productPersistencePort = productPersistencePort;
    }

    @Override
    public Product createProduct(
            String name, String description, ProductType type, Price price, Map<String, Object> attributes) {
        return productPersistencePort.saveProduct(new Product(null, name, description, type, price, Instant.now(),
                                                              null, true, 0, attributes));
    }

    @Transactional
    @Override
    public Product updateProduct(
            UUID id, String name, String description, ProductType type, Price price, Map<String, Object> attributes,
            Boolean isActive) {
        Product product = productPersistencePort.getProductById(id, null);

        if (name != null) {
            product.setName(name);
        }
        if (description != null) {
            product.setDescription(description);
        }
        if (type != null) {
            product.setType(type);
        }
        if (price != null) {
            product.setPrice(price);
        }
        if (attributes != null) {
            product.setAttributes(attributes);
        }
        if (isActive != null) {
            product.setActive(isActive);
        }

        return productPersistencePort.saveProduct(product);
    }

    @Transactional
    @Override
    public void deleteProduct(UUID id) {
        productPersistencePort.deleteProduct(id);
    }

    @Override
    public Product getActiveProductById(UUID id) {
        return productPersistencePort.getProductById(id, true);
    }

    @Override
    public List<Product> getActiveProducts(ProductType type) {
        return productPersistencePort.getProducts(type, true);
    }
}
