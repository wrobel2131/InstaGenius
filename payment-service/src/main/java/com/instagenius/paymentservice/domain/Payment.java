package com.instagenius.paymentservice.domain;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Payment {
    private UUID id;
    private UUID userId;
    private UUID orderId;
    private String orderReferenceId;
    private PaymentStatus status;
    private Price price;
    private Map<String, String> paymentGatewayMetadata;
    private Instant createdAt;
    private Instant updatedAt;
    private int version;

    public Payment() {
    }

    public Payment(UUID id, UUID userId, UUID orderId, String orderReferenceId, PaymentStatus status, Price price,
                   Map<String, String> paymentGatewayMetadata, Instant createdAt, Instant updatedAt, int version) {
        this.id = id;
        this.userId = userId;
        this.orderId = orderId;
        this.orderReferenceId = orderReferenceId;
        this.status = status;
        this.price = price;
        this.paymentGatewayMetadata = paymentGatewayMetadata != null ? paymentGatewayMetadata : new HashMap<>();
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.version = version;
    }

    public Payment(UUID userId, UUID orderId, String orderReferenceId, PaymentStatus status, Price price,
                   Map<String, String> paymentGatewayMetadata,
                   Instant createdAt, Instant updatedAt, int version) {
        this(UUID.randomUUID(), userId, orderId, orderReferenceId, status, price, paymentGatewayMetadata, createdAt, updatedAt, version);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public String getOrderReferenceId() {
        return orderReferenceId;
    }

    public void setOrderReferenceId(String orderReferenceId) {
        this.orderReferenceId = orderReferenceId;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public Map<String, String> getPaymentGatewayMetadata() {
        return paymentGatewayMetadata;
    }

    public void setPaymentGatewayMetadata(Map<String, String> paymentGatewayMetadata) {
        this.paymentGatewayMetadata = paymentGatewayMetadata;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return version == payment.version && Objects.equals(id, payment.id) && Objects.equals(userId,
                                                                                              payment.userId) && Objects.equals(
                orderId, payment.orderId) && Objects.equals(orderReferenceId,
                                                            payment.orderReferenceId) && status == payment.status && Objects.equals(
                price, payment.price) && Objects.equals(paymentGatewayMetadata,
                                                        payment.paymentGatewayMetadata) && Objects.equals(
                createdAt, payment.createdAt) && Objects.equals(updatedAt, payment.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, orderId, orderReferenceId, status, price, paymentGatewayMetadata, createdAt,
                            updatedAt, version);
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", userId=" + userId +
                ", orderId=" + orderId +
                ", orderReferenceId='" + orderReferenceId + '\'' +
                ", status=" + status +
                ", price=" + price +
                ", paymentGatewayMetadata=" + paymentGatewayMetadata +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", version=" + version +
                '}';
    }
}