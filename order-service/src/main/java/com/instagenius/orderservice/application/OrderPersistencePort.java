package com.instagenius.orderservice.application;

import com.instagenius.orderservice.domain.Order;

import java.util.List;
import java.util.UUID;

public interface OrderPersistencePort {
    Order save(Order order);
    Order findOrderByUserIdAndOrderId(UUID userId, UUID orderId);
    List<Order> findOrdersByUserId(UUID userId);
}
