package com.instagenius.postmanagementservice.domain;

import java.util.UUID;

public record CoinReservation(UUID reservationId, UUID userId, int amount) {
}
