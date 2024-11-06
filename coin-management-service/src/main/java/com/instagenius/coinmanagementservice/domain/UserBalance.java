package com.instagenius.coinmanagementservice.domain;

import java.time.Instant;
import java.util.UUID;


public class UserBalance {
    private final UUID id;
    private final UUID userId;
    private Balance availableBalance;
    private Balance reservedBalance;
    private final Instant updatedAt;
    private final Instant createdAt;
    private final int version;


    public UserBalance(UUID id, UUID userId, Balance availableBalance, Balance reservedBalance, Instant updatedAt, Instant createdAt, int version) {
        this.id = id;
        this.userId = userId;
        this.availableBalance = availableBalance;
        this.reservedBalance = reservedBalance;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.version = version;
    }

    public UUID getId() {
        return id;
    }

    public Instant getCreatedAt() {
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

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Balance getAvailableBalance() {
        return availableBalance;
    }
    public int getVersion() {
        return version;
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
                ", version=" + version +
                '}';
    }
}
