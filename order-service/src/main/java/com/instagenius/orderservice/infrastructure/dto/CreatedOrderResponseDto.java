package com.instagenius.orderservice.infrastructure.dto;

public record CreatedOrderResponseDto(String referenceId, String orderStatus, String paymentGatewaySessionId) {
}
