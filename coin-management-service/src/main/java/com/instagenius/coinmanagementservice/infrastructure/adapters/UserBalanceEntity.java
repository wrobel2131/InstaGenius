package com.instagenius.coinmanagementservice.infrastructure.adapters;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "userId", unique = true)
    private UUID userId;

//    @Column(nullable = false, name = "balance")
//    private int balance;

    @Column(nullable = false, name = "available_balance")
    private int availableBalance;

    @Column(nullable = false, name = "reserved_balance")
    private int reservedBalance;

    @Column(nullable = true, name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(nullable = false, name = "created_at")
    private LocalDateTime createdAt;

    @Version
    private int version;

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.reservedBalance = 0;
    }

}
