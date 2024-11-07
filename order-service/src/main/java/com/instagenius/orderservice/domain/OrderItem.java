package com.instagenius.orderservice.domain;

import java.util.UUID;

public record OrderItem(UUID id, UUID productId, int quantity, ) {
}
