package com.instagenius.coinmanagementservice.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class CoinReservation {
    private final Long id;
    private final UUID userId;
    private final CoinAmount amount;
    private final UUID operationId;
    private ReservationStatus status;
    private final LocalDateTime updatedAt;
    private final LocalDateTime createdAt;
    private final LocalDateTime expiryTime;

    public CoinReservation(Long id, UUID userId, CoinAmount amount, UUID operationId, ReservationStatus status, LocalDateTime updatedAt, LocalDateTime createdAt, LocalDateTime expiryTime) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.operationId = operationId;
        this.status = status;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.expiryTime = expiryTime;
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

    public UUID getOperationId() {
        return operationId;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getExpiryTime() {
        return expiryTime;
    }

    @Override
    public String toString() {
        return "CoinReservation{" +
                "id=" + id +
                ", userId=" + userId +
                ", amount=" + amount +
                ", operationId=" + operationId +
                ", status=" + status +
                ", updatedAt=" + updatedAt +
                ", createdAt=" + createdAt +
                ", expiryTime=" + expiryTime +
                '}';
    }
}
