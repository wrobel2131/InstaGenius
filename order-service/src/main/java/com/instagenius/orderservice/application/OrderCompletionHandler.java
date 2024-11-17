package com.instagenius.orderservice.application;

import com.instagenius.orderservice.domain.OrderItem;
import com.instagenius.orderservice.domain.ProductType;

import java.util.UUID;

public interface OrderCompletionHandler {
    ProductType getProductType();
    void handleOrderItemCompletion(UUID userId, OrderItem orderItem);
}
