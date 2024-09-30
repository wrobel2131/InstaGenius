package com.instagenius.coinmanagementservice.domain;


import java.time.LocalDateTime;
import java.util.UUID;

public class CoinTransaction {
    private final Long id;
    private final UUID user_id;
    private final CoinAmount amount;
    private final TransactionType type;
    private final LocalDateTime createdAt;

    public CoinTransaction(Long id, UUID user_id, int amount, TransactionType type, LocalDateTime createdAt) {
        this.id = id;
        this.user_id = user_id;
        this.amount = new CoinAmount(amount);
        this.type = type;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public UUID getUser_id() {
        return user_id;
    }

    public CoinAmount getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
