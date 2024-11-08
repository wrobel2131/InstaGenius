package com.instagenius.orderservice.domain;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record Order(UUID id, UUID userId, OrderStatus status, List<OrderItem> items, Price totalPrice,
                    Instant createdAt, Instant updatedAt) {
}
