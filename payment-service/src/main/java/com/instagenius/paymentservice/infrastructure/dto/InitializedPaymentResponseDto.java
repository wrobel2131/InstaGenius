package com.instagenius.paymentservice.infrastructure.dto;

import java.util.UUID;

public record InitializedPaymentResponseDto(UUID paymentId, String paymentCheckoutSessionId) {
}
