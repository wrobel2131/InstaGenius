package com.instagenius.coinmanagementservice.infrastructure.dto;

import java.util.UUID;

public record CoinReservationDto(Long reservationId, UUID userId, int amount) {
}
