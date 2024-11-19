package com.instagenius.paymentservice.infrastructure.adapter;


import com.instagenius.paymentservice.domain.PaymentStatus;
import com.instagenius.paymentservice.infrastructure.config.PaymentGatewayMetadataConverter;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "payments")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PaymentEntity {
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    @Id
    private UUID id;

    @Column(name = "user_id", nullable = false, updatable = false)
    private UUID userId;

    @Column(name = "order_id", nullable = false, updatable = false)
    private UUID orderId;

    @Column(name = "order_reference_id", nullable = false, updatable = false)
    private String orderReferenceId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PaymentStatus status;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "currency", length = 3, nullable = false)
    private String currency;

    @Column(name = "payment_gateway_metadata", columnDefinition = "TEXT")
    @Convert(converter = PaymentGatewayMetadataConverter.class)
    private Map<String, String> paymentGatewayMetadata = new HashMap<>();

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Version
    @Column(name = "version")
    private int version;

    @PrePersist
    void onCreate() {
        createdAt = Instant.now();
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = Instant.now();
    }
}
