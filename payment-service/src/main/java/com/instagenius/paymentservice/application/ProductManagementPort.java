package com.instagenius.paymentservice.application;


import com.instagenius.paymentservice.domain.Product;

import java.util.List;
import java.util.UUID;

public interface ProductManagementPort {
    List<Product> getProductsByIds(List<UUID> productIds);
}
