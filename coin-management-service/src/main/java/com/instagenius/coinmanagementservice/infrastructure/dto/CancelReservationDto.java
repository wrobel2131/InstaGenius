package com.instagenius.coinmanagementservice.infrastructure.dto;

import jakarta.validation.constraints.NotNull;

public record CancelReservationDto(@NotNull(message = "Reservation Id is required!") Long reservationId) {
}
