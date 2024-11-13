package com.instagenius.orderservice.application;

import com.instagenius.orderservice.domain.Product;

import java.util.List;
import java.util.UUID;

public interface ProductManagementPort {
    List<Product> getProductsByIds(List<UUID> productIds);
}
