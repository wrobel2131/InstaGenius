package com.instagenius.orderservice.infrastructure.dto;

public record CreatedOrderResponseDto(String orderId, String orderStatus, String paymentGatewaySessionId) {
}
