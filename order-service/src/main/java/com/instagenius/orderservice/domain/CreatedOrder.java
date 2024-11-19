package com.instagenius.orderservice.domain;

public record CreatedOrder(String referenceId, OrderStatus orderStatus, String paymentGatewaySessionId) {
}
