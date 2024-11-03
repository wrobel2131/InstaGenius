package com.instagenius.coinmanagementservice.infrastructure.dto;

import com.instagenius.coinmanagementservice.domain.Price;
import com.instagenius.coinmanagementservice.domain.TransactionType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


public record AddCoinsDto(@Positive(message = "Coins amount needs to be positive!") int coins, @NotNull(message = "Price is required!") Price price, @NotNull(message = "Transaction type is required!") TransactionType type) {
}
