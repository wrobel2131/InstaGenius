package com.instagenius.paymentservice.infrastructure.dto;

import java.util.UUID;

public record CreatedPaymentDto(UUID paymentId, String paymentGatewaySessionId) {
}
