package com.instagenius.orderservice.infrastructure.dto;

import com.instagenius.orderservice.domain.ProductType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record OrderItemRequestDto(@NotNull(message = "Order item id is required!") UUID id, @NotNull(message = "Order item type is required!") ProductType type, @Positive(message = "Number of products needs to be positive!") int quantity) {
}
