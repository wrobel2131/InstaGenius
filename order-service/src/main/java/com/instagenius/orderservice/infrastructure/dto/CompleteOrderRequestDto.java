package com.instagenius.orderservice.infrastructure.dto;

import java.util.UUID;

public record CompleteOrderRequestDto(UUID paymentId) {
}
