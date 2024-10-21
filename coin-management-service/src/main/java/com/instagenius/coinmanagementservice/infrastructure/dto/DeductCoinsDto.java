package com.instagenius.coinmanagementservice.infrastructure.dto;

import jakarta.validation.constraints.NotNull;

public record DeductCoinsDto(@NotNull int coins) {
}
