package com.instagenius.coinmanagementservice.infrastructure.adapters;

import com.instagenius.coinmanagementservice.domain.ReservationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Column(nullable = false, name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(nullable = false, name = "created_at")
    private LocalDateTime createdAt;

    @Column(nullable = false, name = "expiry_time")
    private LocalDateTime expiryTime;

    @Version
    private Long version;

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
