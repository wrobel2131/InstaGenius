package com.instagenius.paymentservice.infrastructure.dto;

import java.util.UUID;

public record CompleteOrderRequestDto(UUID paymentId) {
}
