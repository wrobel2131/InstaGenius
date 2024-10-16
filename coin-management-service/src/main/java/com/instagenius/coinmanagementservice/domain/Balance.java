package com.instagenius.coinmanagementservice.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public record Balance(int balance) {

    public Balance {
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }
    }
}
