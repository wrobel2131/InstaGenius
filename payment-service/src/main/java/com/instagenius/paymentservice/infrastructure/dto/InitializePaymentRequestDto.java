package com.instagenius.paymentservice.infrastructure.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record InitializePaymentRequestDto(@NotNull(message = "Order Id is required!") UUID orderId,
                                          @NotNull(message = "Reference id is required!") String referenceId,
                                          List<OrderedProductRequestDto> orderedProducts) {
}
