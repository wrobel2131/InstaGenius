package com.instagenius.orderservice.application;

import java.util.UUID;

public interface OrderUseCase {
    Order createOrder(UUID productId);
}
