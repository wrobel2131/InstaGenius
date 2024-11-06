package com.instagenius.coinmanagementservice.domain;

import java.time.Instant;
import java.util.UUID;

public class CoinReservation {
    private final UUID id;
    private final UUID userId;
    private final CoinAmount amount;
    private final UUID operationId;
    private ReservationStatus status;
    private final Instant updatedAt;
    private final Instant createdAt;
    private final Instant expiryTime;
    private final int version;


    public CoinReservation(UUID id, UUID userId, CoinAmount amount, UUID operationId, ReservationStatus status, Instant updatedAt, Instant createdAt, Instant expiryTime, int version) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.operationId = operationId;
        this.status = status;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.expiryTime = expiryTime;
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

    public UUID getOperationId() {
        return operationId;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getExpiryTime() {
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
                ", version=" + version +
                '}';
    }
}
