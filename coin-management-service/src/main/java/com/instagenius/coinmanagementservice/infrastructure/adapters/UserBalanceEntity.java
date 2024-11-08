package com.instagenius.coinmanagementservice.infrastructure.adapters;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Table(name = "user_balance")
public class UserBalanceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(nullable = false, name = "user_id", unique = true)
    private UUID userId;

    @Column(nullable = false, name = "available_balance")
    private int availableBalance;

    @Column(nullable = false, name = "reserved_balance")
    private int reservedBalance;

    @Column(nullable = true, name = "updated_at")
    private Instant updatedAt;

    @Column(nullable = false, name = "created_at")
    private Instant createdAt;

    @Version
    @Column(name = "version", nullable = false)
    private int version;

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
        this.reservedBalance = 0;
    }

}
