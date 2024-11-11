package com.instagenius.coinmanagementservice.infrastructure.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

public record CreateCoinPackageRequestDto(@NotNull(message = "Coin package name is required!") String name,
                                          String description,
                                          @Positive(message = "Coin amount needs to be positive number!") int coinAmount,
                                          @NotNull(message = "Price is required!") @PositiveOrZero(message = "Price cannot be negative number!") BigDecimal price,
                                          @NotNull(message = "Currency is required!") @Length(min = 3, max = 3,
                                                  message = "Currency code needs to be exactly 3 characters long!") String currency) {
}
