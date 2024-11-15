package com.instagenius.orderservice.infrastructure.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record OrderResponseDto(String referenceId, Instant createdAt, String status, BigDecimal totalPrice, String currency,
                               List<OrderItemDto> items) {
}
