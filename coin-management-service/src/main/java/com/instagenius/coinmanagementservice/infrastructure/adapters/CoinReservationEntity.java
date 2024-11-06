package com.instagenius.coinmanagementservice.infrastructure.adapters;

import com.instagenius.coinmanagementservice.domain.ReservationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Entity
@Table(name = "coin_reservation")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class CoinReservationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, name = "userId")
    private UUID userId;

    @Column(nullable = false, name = "amount")
    private int amount;

    /* Operation id is unique value, which identifies the operation */
    @Column(nullable = false, name = "operationId", unique = true)
    private UUID operationId;

    @Column(nullable = false, name = "status")
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @Column(nullable = true, name = "updated_at")
    private Instant updatedAt;

    @Column(nullable = false, name = "created_at")
    private Instant createdAt;

    @Column(nullable = false, name = "expiry_time")
    private Instant expiryTime;

    @Version
    @Column(nullable = false, name = "version")
    private int version;

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
        this.expiryTime = Instant.now().plus(10, ChronoUnit.MINUTES);
    }
}
