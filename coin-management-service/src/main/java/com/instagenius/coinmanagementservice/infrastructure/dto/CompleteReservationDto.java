package com.instagenius.coinmanagementservice.infrastructure.dto;

import jakarta.validation.constraints.NotNull;

public record CompleteReservationDto(@NotNull(message = "Reservation Id is required!")  Long reservationId) {
}
