package com.instagenius.coinmanagementservice.domain;


import java.time.LocalDateTime;
import java.util.UUID;

public class CoinTransaction {
    private final Long id;
    private final UUID userId;
    private final CoinAmount amount;
    private final TransactionType type;
    private final LocalDateTime createdAt;

    public CoinTransaction(Long id, UUID userId, CoinAmount amount, TransactionType type, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.type = type;
        this.createdAt = createdAt;
    }

    public Long getId() {
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "CoinTransaction{" +
                "id=" + id +
                ", userId=" + userId +
                ", amount=" + amount +
                ", type=" + type +
                ", createdAt=" + createdAt +
                '}';
    }
}
