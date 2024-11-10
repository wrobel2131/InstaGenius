package com.instagenius.orderservice.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class OrderItem {

    private final UUID id;
    private final Product product;
    private final int quantity;
    private final Price unitPrice;
    private final Price totalPrice;
    private final Instant createdAt;
    private final int version;
    private final UUID orderId;

    public OrderItem(UUID id, Product product, int quantity, Price unitPrice, Price totalPrice, Instant createdAt,
                     int version, UUID orderId) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
        this.version = version;
        this.orderId = orderId;
    }

    public OrderItem(UUID id, UUID productId, String productName, String productDescription, ProductType productType,
                     int quantity, Price unitPrice, Instant createdAt, UUID orderId) {

        this(id, new Product(productId, productName, productDescription, productType, unitPrice),
             quantity, unitPrice, calculateTotalPrice(unitPrice, quantity), createdAt, 0, orderId);
    }

    private static Price calculateTotalPrice(Price unitPrice, int quantity) {
        BigDecimal totalAmount = unitPrice.price().multiply(BigDecimal.valueOf(quantity));
        return new Price(totalAmount, unitPrice.currency());
    }

    public UUID getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public Price getUnitPrice() {
        return unitPrice;
    }

    public Price getTotalPrice() {
        return totalPrice;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
