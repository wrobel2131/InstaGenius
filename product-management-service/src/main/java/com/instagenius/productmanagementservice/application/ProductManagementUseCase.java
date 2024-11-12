package com.instagenius.productmanagementservice.application;

import com.instagenius.productmanagementservice.domain.Price;
import com.instagenius.productmanagementservice.domain.Product;
import com.instagenius.productmanagementservice.domain.ProductType;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ProductManagementUseCase {
    Product createProduct(String name, String description, ProductType type, Price price, Map<String, Object> attributes);
    Product updateProduct(UUID id, String name, String description, ProductType type, Price price, Map<String, Object> attributes, Boolean isActive);
    void deleteProduct(UUID id);
    Product getActiveProductById(UUID id);
    List<Product> getActiveProducts(ProductType type);
}
