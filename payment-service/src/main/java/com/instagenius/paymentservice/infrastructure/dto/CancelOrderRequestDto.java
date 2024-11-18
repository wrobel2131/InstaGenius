package com.instagenius.paymentservice.infrastructure.dto;

import java.util.UUID;

public record CancelOrderRequestDto(UUID paymentId) {
}
