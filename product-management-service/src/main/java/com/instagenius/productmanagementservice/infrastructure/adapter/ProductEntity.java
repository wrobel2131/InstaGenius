package com.instagenius.productmanagementservice.infrastructure.adapter;


import com.instagenius.productmanagementservice.domain.ProductType;
import com.instagenius.productmanagementservice.infrastructure.config.ProductAttributesConverter;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
@Table(name = "products")
@Entity
public class ProductEntity {
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "product_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductType type;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "currency", nullable = false)
    private String currency;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "version")
    private int version;

    @Convert(converter = ProductAttributesConverter.class)
    @Column(name = "attributes", columnDefinition = "TEXT")
    private Map<String, Object> attributes = new HashMap<>();

    @Convert(converter = ProductAttributesConverter.class)
    @Column(name = "payment_gateway_product_params", columnDefinition = "TEXT")
    private Map<String, Object> paymentGatewayProductParams;


    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
    }

    @PostPersist
    protected void onPostCreate() {
        /* id should be set in domain object first, if not, then it's generated here */
        if (id == null) {
            id = UUID.randomUUID();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }
}
