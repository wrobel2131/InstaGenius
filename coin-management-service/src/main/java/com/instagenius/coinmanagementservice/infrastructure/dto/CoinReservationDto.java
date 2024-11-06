package com.instagenius.coinmanagementservice.infrastructure.dto;

import java.util.UUID;

public record CoinReservationDto(UUID reservationId, UUID userId, int amount) {
}
