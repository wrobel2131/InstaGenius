package com.instagenius.coinmanagementservice.infrastructure.dto;

import jakarta.validation.constraints.NotNull;

public record CreateUserBalanceDto(@NotNull int initialBalance) {
}
