package com.instagenius.coinmanagementservice.infrastructure.dto;

import com.instagenius.coinmanagementservice.domain.Price;
import com.instagenius.coinmanagementservice.domain.TransactionType;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record AddCoinsDto(@NotNull int coins, @NotNull Price price, @NotNull TransactionType type) {
}
