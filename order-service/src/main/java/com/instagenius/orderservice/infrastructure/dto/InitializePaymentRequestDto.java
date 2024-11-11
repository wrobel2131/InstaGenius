package com.instagenius.orderservice.infrastructure.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record InitializePaymentRequestDto(UUID orderId, BigDecimal price, String currency) {
}
