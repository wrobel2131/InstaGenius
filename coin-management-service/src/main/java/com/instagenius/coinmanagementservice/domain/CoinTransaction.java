package com.instagenius.coinmanagementservice.domain;


import java.time.Instant;
import java.util.UUID;

public class CoinTransaction {
    private final UUID id;
    private final UUID userId;
    private final CoinAmount amount;
    private final TransactionType type;
    private final Instant createdAt;
    private final int version;

    public CoinTransaction(UUID id, UUID userId, CoinAmount amount, TransactionType type, Instant createdAt, int version) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.type = type;
        this.createdAt = createdAt;
        this.version = version;
    }

    public int getVersion() {
        return version;
    }

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public CoinAmount getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "CoinTransaction{" +
                "id=" + id +
                ", userId=" + userId +
                ", price=" + amount +
                ", type=" + type +
                ", createdAt=" + createdAt +
                '}';
    }
}
