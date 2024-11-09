package com.instagenius.orderservice.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class Order {

    private final UUID id;
    private final String orderId;
    private final UUID userId;
    private final OrderStatus status;
    private final List<OrderItem> items;
    private final Price totalPrice;
    private final Instant createdAt;
    private final Instant updatedAt;

    public Order(UUID userId, OrderStatus status, List<OrderItem> items) {
        this(null, generateOrderId(), userId, status, items, null, null);
    }

    public Order(UUID id, String orderId, UUID userId, OrderStatus status, List<OrderItem> items,
                 Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.orderId = orderId;
        this.userId = userId;
        this.status = status;
        this.items = List.copyOf(items);
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

        validateItems();
        this.totalPrice = calculateTotalPrice();
    }

    public UUID getId() {
        return id;
    }

    public String getOrderId() {
        return orderId;
    }

    public UUID getUserId() {
        return userId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public Price getTotalPrice() {
        return totalPrice;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    private static String generateOrderId() {
        long timestamp = Instant.now().toEpochMilli();
        int randomNum = ThreadLocalRandom.current().nextInt(1000, 9999999);
        return "ORDER_" + timestamp + "_" + randomNum;
    }

    private void validateItems() {
        if (items == null || items.isEmpty()) {
            return;
        }

        String currency = items.getFirst().getUnitPrice().currency();

        boolean allMatch = items.stream()
                                .allMatch(item -> Objects.equals(item.getUnitPrice().currency(), currency));

        if (!allMatch) {
            throw new IllegalArgumentException("All items must have the same currency.");
        }
    }

    private Price calculateTotalPrice() {
        String currency = items.getFirst().getUnitPrice().currency();

        BigDecimal totalAmount = items.stream()
                                      .map(OrderItem::getTotalPrice)
                                      .map(Price::price)
                                      .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new Price(totalAmount, currency);
    }
}
