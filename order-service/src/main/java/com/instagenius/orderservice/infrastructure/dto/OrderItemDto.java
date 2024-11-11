package com.instagenius.orderservice.infrastructure.dto;

import java.math.BigDecimal;
import java.util.Map;

public record OrderItemDto(String productName, String productDescription, int quantity, BigDecimal unitPrice, BigDecimal totalPrice, String currency, Map<String, Object> attributes) {
}
