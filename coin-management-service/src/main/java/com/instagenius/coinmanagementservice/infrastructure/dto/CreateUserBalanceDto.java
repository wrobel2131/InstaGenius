package com.instagenius.coinmanagementservice.infrastructure.dto;

import jakarta.validation.constraints.PositiveOrZero;

public record CreateUserBalanceDto(@PositiveOrZero(message = "Initial balance needs to be positive!") int initialBalance) {
}
