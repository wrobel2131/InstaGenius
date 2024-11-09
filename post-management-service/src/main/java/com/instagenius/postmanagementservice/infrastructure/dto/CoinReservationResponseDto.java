package com.instagenius.postmanagementservice.infrastructure.dto;

import java.util.UUID;

public record CoinReservationResponseDto(UUID reservationId, UUID userId, int amount) {
}
