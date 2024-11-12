package com.instagenius.productmanagementservice.application;

import com.instagenius.productmanagementservice.domain.Product;
import com.instagenius.productmanagementservice.domain.ProductType;

import java.util.List;
import java.util.UUID;

public interface ProductPersistencePort {
    Product saveProduct(Product product);
    Product getProductById(UUID id, Boolean active);
    void deleteProduct(UUID id);
    List<Product> getProducts(ProductType type, Boolean active);
}
