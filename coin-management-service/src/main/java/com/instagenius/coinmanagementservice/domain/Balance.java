package com.instagenius.coinmanagementservice.domain;

public record Balance(int balance) {

    public Balance {
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }
    }
}
