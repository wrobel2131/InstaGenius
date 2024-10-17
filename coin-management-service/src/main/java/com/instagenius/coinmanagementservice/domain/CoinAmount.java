package com.instagenius.coinmanagementservice.domain;


public record CoinAmount(int amount) {
    public CoinAmount {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
    }
}
