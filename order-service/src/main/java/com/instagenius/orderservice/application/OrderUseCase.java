package com.instagenius.orderservice.application;

import com.instagenius.orderservice.domain.Order;

import java.util.UUID;

public interface OrderUseCase {
    Order createOrder(UUID productId);
}
