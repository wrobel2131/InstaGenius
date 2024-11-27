package com.instagenius.notificationservice.infrastructure.adapter;

import com.instagenius.notificationservice.domain.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "order_notifications")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderNotificationEntity {
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "order_reference_id", nullable = false)
    private String orderReferenceId;

    @Column(name = "user_email", nullable = false)
    private String user_email;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    private OrderStatus orderStatus;
    
    @Version
    @Column(name = "version")
    private int version;

    @PrePersist
    public void onCreate() {
        this.createdAt = Instant.now();
    }
}
