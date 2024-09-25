package com.instagenius.coinmanagementservice.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserBalance {
    private final Long id;
    private final UUID userId;
    private Balance balance;
    private LocalDateTime updatedAt;


    public UserBalance(Long id, UUID userId, Balance balance, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.balance = balance;
        this.updatedAt = updatedAt;
    }

    public void setBalance(Balance balance) {
        this.balance = balance;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
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
}
