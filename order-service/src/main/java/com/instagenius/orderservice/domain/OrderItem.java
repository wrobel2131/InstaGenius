package com.instagenius.orderservice.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

public class OrderItem {

    private final UUID id;
    private final Product product;
    private int quantity;
    private Price unitPrice;
    private Price totalPrice;
    private final Instant createdAt;

    // Constructor that matches the record's canonical constructor
    public OrderItem(UUID id, Product product, int quantity, Price unitPrice, Price totalPrice, Instant createdAt) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
    }

    public OrderItem(UUID id, UUID productId, String productName, String productDescription, ProductType productType,
                     int quantity, Price unitPrice, Map<String, Object> details, Instant createdAt) {

        this(id, new Product(productId, productName, productDescription, productType, unitPrice, details),
             quantity, unitPrice, calculateTotalPrice(unitPrice, quantity), createdAt);
    }

    private static Price calculateTotalPrice(Price unitPrice, int quantity) {
        BigDecimal totalAmount = unitPrice.price().multiply(BigDecimal.valueOf(quantity));
        return new Price(totalAmount, unitPrice.currency());
    }

    // Getters
    public UUID getId() {
        return id;
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
