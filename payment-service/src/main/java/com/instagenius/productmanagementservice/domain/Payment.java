package com.instagenius.productmanagementservice.domain;

import java.util.UUID;

public record Payment(UUID id, UUID userId, UUID orderId, PaymentStatus status, Price price, String currency) {
}
