package com.instagenius.coinmanagementservice.infrastructure.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

public record UpdateCoinPackageRequestDto(String name, String description,
                                          @Positive(message = "Coin amount needs to be positive number!") Integer coinAmount,
                                          @PositiveOrZero(message = "Price cannot be negative number!") BigDecimal price,
                                          @Length(message = "Currency needs to be exactly 3 characters long!", min =
                                                  3, max = 3) String currency, Boolean isActive) {
}
