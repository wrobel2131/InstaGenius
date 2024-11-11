package com.instagenius.orderservice.domain;

public record CreatedOrder(String orderId, OrderStatus orderStatus, String paymentGatewaySessionId) {
}
