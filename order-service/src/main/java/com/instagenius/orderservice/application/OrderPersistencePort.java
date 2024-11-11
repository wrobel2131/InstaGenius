package com.instagenius.orderservice.application;

import com.instagenius.orderservice.domain.Order;

import java.util.List;
import java.util.UUID;

public interface OrderPersistencePort {
    Order save(Order order);
    Order findOrderByUserIdAndOrderId(UUID userId, String orderId);
    Order findOrderByUserIdAndId(UUID userId, UUID id);
    List<Order> findOrdersByUserId(UUID userId);
}
