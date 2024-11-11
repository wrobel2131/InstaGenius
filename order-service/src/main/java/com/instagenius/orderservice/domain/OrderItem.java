package com.instagenius.orderservice.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class OrderItem {

    private final UUID id;
    private final Product product;
    private final int quantity;
    private final Price unitPrice;
    private final Price totalPrice;
    private final Instant createdAt;
    private final int version;

    public OrderItem(UUID id, Product product, int quantity, Price unitPrice, Price totalPrice, Instant createdAt,
                     int version) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
        this.version = version;
    }

    public OrderItem(UUID id, UUID productId, String productName, String productDescription, ProductType productType,
                     int quantity, Price unitPrice, Instant createdAt, Map<String, Object> attributes) {

        this(id, new Product(productId, productName, productDescription, productType, unitPrice, attributes),
             quantity, unitPrice, calculateTotalPrice(unitPrice, quantity), createdAt, 0);
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

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", product=" + product +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", totalPrice=" + totalPrice +
                ", createdAt=" + createdAt +
                ", version=" + version +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return quantity == orderItem.quantity && version == orderItem.version && Objects.equals(id,
                                                                                                orderItem.id) && Objects.equals(
                product, orderItem.product) && Objects.equals(unitPrice,
                                                              orderItem.unitPrice) && Objects.equals(
                totalPrice, orderItem.totalPrice) && Objects.equals(createdAt, orderItem.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, product, quantity, unitPrice, totalPrice, createdAt, version);
    }
}
