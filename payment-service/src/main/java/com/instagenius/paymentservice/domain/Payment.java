package com.instagenius.paymentservice.domain;

import java.util.UUID;

public record Payment(UUID id, UUID userId, UUID orderId, PaymentStatus status, Price price, String currency) {
}
