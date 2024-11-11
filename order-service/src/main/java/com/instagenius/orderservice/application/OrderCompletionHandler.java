package com.instagenius.orderservice.application;

import com.instagenius.orderservice.domain.OrderItem;
import com.instagenius.orderservice.domain.ProductType;

public interface OrderCompletionHandler {
    ProductType getProductType();
    void handleOrderItemCompletion(OrderItem orderItem);
}
