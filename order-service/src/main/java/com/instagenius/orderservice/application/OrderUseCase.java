package com.instagenius.orderservice.application;

import com.instagenius.orderservice.domain.Order;
import com.instagenius.orderservice.domain.OrderedProduct;

import java.util.List;
import java.util.UUID;

public interface OrderUseCase {
    Order createOrder(List<OrderedProduct> orderedProducts, UUID userId);
}
