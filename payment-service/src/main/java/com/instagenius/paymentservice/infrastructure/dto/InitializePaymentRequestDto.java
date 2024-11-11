package com.instagenius.paymentservice.infrastructure.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record InitializePaymentRequestDto(@NotNull(message = "Order Id is required!") UUID orderId,
                                          @NotNull(message = "Price is required!") @Positive(message = "Price needs to be positive number!")
                                          BigDecimal price,
                                          @NotNull(message = "Currency is required!") String currency) {
}
