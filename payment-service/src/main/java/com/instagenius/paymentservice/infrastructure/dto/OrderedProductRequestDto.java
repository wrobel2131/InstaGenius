package com.instagenius.paymentservice.infrastructure.dto;

import java.util.UUID;

public record OrderedProductRequestDto(UUID productId, int quantity) {
}
