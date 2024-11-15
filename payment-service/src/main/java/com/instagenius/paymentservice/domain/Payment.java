package com.instagenius.paymentservice.domain;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class Payment {
    private UUID id;
    private UUID userId;
    private UUID orderId;
    private String orderReferenceId;
    private PaymentStatus status;
    private Price price;
    private String paymentGatewayCheckoutSessionId;
    private String paymentGatewayPaymentId;
    private String paymentMethod;
    private Instant createdAt;
    private Instant updatedAt;
    private int version;

    public Payment(UUID id, UUID userId, UUID orderId, String orderReferenceId, PaymentStatus status, Price price,
                   String paymentGatewayCheckoutSessionId, String paymentGatewayPaymentId, String paymentMethod,
                   Instant createdAt, Instant updatedAt, int version) {
        this.id = id;
        this.userId = userId;
        this.orderId = orderId;
        this.orderReferenceId = orderReferenceId;
        this.status = status;
        this.price = price;
        this.paymentGatewayCheckoutSessionId = paymentGatewayCheckoutSessionId;
        this.paymentGatewayPaymentId = paymentGatewayPaymentId;
        this.paymentMethod = paymentMethod;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.version = version;
    }

    public Payment(UUID userId, UUID orderId, String orderReferenceId, PaymentStatus status, Price price,
                   String paymentGatewayCheckoutSessionId, String paymentGatewayPaymentId, String paymentMethod,
                   Instant createdAt, Instant updatedAt, int version) {
        this(UUID.randomUUID(), userId, orderId, orderReferenceId, status, price, paymentGatewayCheckoutSessionId, paymentGatewayPaymentId, paymentMethod, createdAt, updatedAt, version);
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

    public String getPaymentGatewayCheckoutSessionId() {
        return paymentGatewayCheckoutSessionId;
    }

    public void setPaymentGatewayCheckoutSessionId(String paymentGatewayCheckoutSessionId) {
        this.paymentGatewayCheckoutSessionId = paymentGatewayCheckoutSessionId;
    }

    public String getPaymentGatewayPaymentId() {
        return paymentGatewayPaymentId;
    }

    public void setPaymentGatewayPaymentId(String paymentGatewayPaymentId) {
        this.paymentGatewayPaymentId = paymentGatewayPaymentId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
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

    String getOrderReferenceId() {
        return orderReferenceId;
    }

    void setOrderReferenceId(String orderReferenceId) {
        this.orderReferenceId = orderReferenceId;
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
                ", paymentGatewayCheckoutSessionId='" + paymentGatewayCheckoutSessionId + '\'' +
                ", paymentGatewayPaymentId='" + paymentGatewayPaymentId + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", version=" + version +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return version == payment.version && Objects.equals(id, payment.id) &&
                Objects.equals(userId, payment.userId) && Objects.equals(orderId, payment.orderId) &&
                Objects.equals(orderReferenceId, payment.orderReferenceId) && status == payment.status &&
                Objects.equals(price, payment.price) &&
                Objects.equals(paymentGatewayCheckoutSessionId, payment.paymentGatewayCheckoutSessionId) &&
                Objects.equals(paymentGatewayPaymentId, payment.paymentGatewayPaymentId) &&
                Objects.equals(paymentMethod, payment.paymentMethod) &&
                Objects.equals(createdAt, payment.createdAt) &&
                Objects.equals(updatedAt, payment.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, orderId, orderReferenceId, status, price,
                paymentGatewayCheckoutSessionId,
                paymentGatewayPaymentId, paymentMethod, createdAt, updatedAt, version);
    }
}