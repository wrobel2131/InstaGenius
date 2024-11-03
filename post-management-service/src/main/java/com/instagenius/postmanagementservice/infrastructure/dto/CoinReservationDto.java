package com.instagenius.postmanagementservice.infrastructure.dto;

import java.util.UUID;

public record CoinReservationDto(Long reservationId, UUID userId, int amount) {
}
