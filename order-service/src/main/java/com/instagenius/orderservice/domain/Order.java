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
    private OrderStatus status;
    private final List<OrderItem> items;
    private final Price totalPrice;
    private final Instant createdAt;
    private final Instant updatedAt;
    private final int version;
    private UUID paymentId;

    public Order(UUID userId, OrderStatus status, List<OrderItem> items, UUID paymentId) {
        this(null, generateOrderId(), userId, status, items, null, null, 0, paymentId);
    }

    public Order(UUID id, String orderId, UUID userId, OrderStatus status, List<OrderItem> items,
                 Instant createdAt, Instant updatedAt, int version, UUID paymentId) {
        this.id = id;
        this.orderId = orderId;
        this.userId = userId;
        this.status = status;
        this.items = List.copyOf(items);
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.version = version;
        this.paymentId = paymentId;

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

    public int getVersion() {
        return version;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void setPaymentId(UUID paymentId) {
        this.paymentId = paymentId;
    }

    public UUID getPaymentId() {
        return paymentId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return version == order.version && Objects.equals(id, order.id) && Objects.equals(orderId,
                                                                                          order.orderId) && Objects.equals(
                userId, order.userId) && status == order.status && Objects.equals(items,
                                                                                  order.items) && Objects.equals(
                totalPrice, order.totalPrice) && Objects.equals(createdAt,
                                                                order.createdAt) && Objects.equals(
                updatedAt, order.updatedAt) && Objects.equals(paymentId, order.paymentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderId, userId, status, items, totalPrice, createdAt, updatedAt, version, paymentId);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderId='" + orderId + '\'' +
                ", userId=" + userId +
                ", status=" + status +
                ", items=" + items +
                ", totalPrice=" + totalPrice +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", version=" + version +
                ", paymentId=" + paymentId +
                '}';
    }
}
