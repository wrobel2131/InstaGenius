package com.instagenius.orderservice.domain;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record OrderConfirmation(UUID id, String referenceId, UUID userId, OrderStatus orderStatus, List<OrderItem> items,
                                Price totalPrice, Instant createdAt, Instant updatedAt, UUID paymentId) {
}
