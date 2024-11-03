package com.instagenius.coinmanagementservice.domain;

import java.math.BigDecimal;

public record Price(BigDecimal amount, String currency) {
    public Price {
        if (amount == null || BigDecimal.ZERO.compareTo(amount) >= 0) {
            throw new IllegalArgumentException("Price must be greater than zero!");
        }
    }
}
