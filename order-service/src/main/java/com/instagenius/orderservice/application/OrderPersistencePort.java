package com.instagenius.orderservice.application;

import com.instagenius.orderservice.domain.Order;

import java.util.List;
import java.util.UUID;

public interface OrderPersistencePort {
    Order save(Order order);
    Order findOrderByUserIdAndReferenceId(UUID userId, String referenceId);
    Order findOrderByUserIdAndId(UUID userId, UUID id);
    Order findOrderById(UUID id);
    List<Order> findOrdersByUserId(UUID userId);
}
