package com.instagenius.orderservice.infrastructure.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateOrderRequestDto(@NotNull(message = "Items are required!") List<OrderedProductRequestDto> items) {
}
