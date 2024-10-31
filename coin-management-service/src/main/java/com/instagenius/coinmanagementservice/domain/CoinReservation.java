package com.instagenius.coinmanagementservice.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class CoinReservation {
    private Long id;
    private UUID userId;
    private CoinAmount amount;
    private UUID operationId;
    private ReservationStatus status;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;

    public CoinReservation(Long id, UUID userId, CoinAmount amount, UUID operationId, ReservationStatus status, LocalDateTime updatedAt, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.operationId = operationId;
        this.status = status;
        this.updatedAt = updatedAt;
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
                '}';
    }
}
