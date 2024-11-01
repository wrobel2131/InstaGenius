package com.instagenius.postmanagementservice.domain;

import java.util.UUID;

public record CoinReservation(Long reservationId, UUID userId, int amount) {
}
