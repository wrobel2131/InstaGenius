package com.instagenius.productmanagementservice.infrastructure.dto;

import java.util.UUID;

public record CreatedPaymentDto(UUID paymentId, String paymentGatewaySessionId) {
}
