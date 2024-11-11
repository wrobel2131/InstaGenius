package com.instagenius.coinmanagementservice.domain;

import java.math.BigDecimal;

public record Price(BigDecimal price, String currency) {
    public Price {
        if (price == null || BigDecimal.ZERO.compareTo(price) >= 0) {
            throw new IllegalArgumentException("Price must be greater than zero!");
        }
    }
}
