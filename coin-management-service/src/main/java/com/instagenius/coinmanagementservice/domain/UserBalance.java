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

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public UUID getUserId() {
        return userId;
    }

    public Balance getReservedBalance() {
        return reservedBalance;
    }

    public void setAvailableBalance(Balance availableBalance) {
        this.availableBalance = availableBalance;
    }

    public void setReservedBalance(Balance reservedBalance) {
        this.reservedBalance = reservedBalance;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Balance getAvailableBalance() {
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
