package com.instagenius.coinmanagementservice.infrastructure.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record AddCoinsDto(@NotNull int coins, @NotNull BigDecimal price) { //TODO think about the price, maybe it could be separate DTO with price, currency etc
}
