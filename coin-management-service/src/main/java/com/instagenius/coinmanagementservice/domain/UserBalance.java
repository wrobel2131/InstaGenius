package com.instagenius.coinmanagementservice.domain;

import java.time.LocalDateTime;
import java.util.UUID;


public class UserBalance {
    private final Long id;
    private final UUID userId;
    private Balance balance;
    private LocalDateTime updatedAt;
    private final LocalDateTime createdAt;
    private final int version;


    public UserBalance(Long id, UUID userId, Balance balance, LocalDateTime updatedAt, LocalDateTime createdAt, int version) {
        this.id = id;
        this.userId = userId;
        this.balance = balance;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.version = version;
    }

    public void setBalance(Balance balance) {
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public Balance getBalance() {
        return balance;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public int getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return "UserBalance{" +
                "id=" + id +
                ", userId=" + userId +
                ", balance=" + balance +
                ", updatedAt=" + updatedAt +
                ", createdAt=" + createdAt +
                ", version=" + version +
                '}';
    }
}
