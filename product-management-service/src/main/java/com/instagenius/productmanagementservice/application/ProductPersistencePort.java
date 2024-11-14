package com.instagenius.productmanagementservice.application;

import com.instagenius.productmanagementservice.domain.Product;
import com.instagenius.productmanagementservice.domain.ProductType;

import java.util.List;
import java.util.UUID;

public interface ProductPersistencePort {
    Product saveProduct(Product product);
    Product getProductById(UUID id, Boolean active);
    List<Product> getProducts(ProductType type, Boolean active);
    List<Product> getProductsByIds(List<UUID> ids);
}
