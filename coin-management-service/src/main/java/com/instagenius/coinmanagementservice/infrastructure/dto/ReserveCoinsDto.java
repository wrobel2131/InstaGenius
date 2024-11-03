package com.instagenius.coinmanagementservice.infrastructure.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record ReserveCoinsDto(@Positive(message = "Reserved coins need to be positive!") int amount, @NotNull(message = "Operation Id is required!") UUID operationId) {
}
