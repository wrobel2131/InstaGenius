package com.instagenius.orderservice.infrastructure.dto;

import java.util.UUID;

public record CreatedPaymentResponseDto(UUID paymentId, String paymentCheckoutSessionId) {
}
