package com.instagenius.postmanagementservice.infrastructure.dto;

import java.util.UUID;

public record CoinReservationDto(UUID reservationId, UUID userId, int amount) {
}
