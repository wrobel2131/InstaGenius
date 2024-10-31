package com.instagenius.coinmanagementservice.domain;

import java.time.LocalDateTime;
import java.util.UUID;


public class UserBalance {
    private final Long id;
    private final UUID userId;
    private Balance availableBalance;
    private Balance reservedBalance;
    private final LocalDateTime updatedAt;
    private final LocalDateTime createdAt;


    public UserBalance(Long id, UUID userId, Balance availableBalance, Balance reservedBalance, LocalDateTime updatedAt, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.availableBalance = availableBalance;
        this.reservedBalance = reservedBalance;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }

    Long getId() {
        return id;
    }

    LocalDateTime getCreatedAt() {
        return createdAt;
    }

    UUID getUserId() {
        return userId;
    }

    Balance getReservedBalance() {
        return reservedBalance;
    }

    void setAvailableBalance(Balance availableBalance) {
        this.availableBalance = availableBalance;
    }

    void setReservedBalance(Balance reservedBalance) {
        this.reservedBalance = reservedBalance;
    }

    LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    Balance getAvailableBalance() {
        return availableBalance;
    }

    @Override
    public String toString() {
        return "UserBalance{" +
                "id=" + id +
                ", userId=" + userId +
                ", availableBalance=" + availableBalance +
                ", reservedBalance=" + reservedBalance +
                ", updatedAt=" + updatedAt +
                ", createdAt=" + createdAt +
                '}';
    }
}
