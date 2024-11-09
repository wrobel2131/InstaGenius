package com.instagenius.orderservice.infrastructure.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record CreateOrderRequestDto(@NotNull(message = "Items are required!") List<OrderItemRequestDto> items) {
}
