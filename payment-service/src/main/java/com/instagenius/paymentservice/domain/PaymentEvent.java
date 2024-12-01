package com.instagenius.paymentservice.domain;

import java.time.Instant;
import java.util.UUID;

public class PaymentEvent {
    private final UUID paymentId;
    private final String userEmail;
    private final String userFirstName;
    private final String userLastName;
    private final PaymentStatus paymentStatus;
    private final Price price;
    private final Instant createdAt;
    private final String paymentUrl;

    public PaymentEvent(
            UUID paymentId, String userEmail, String userFirstName, String userLastName, PaymentStatus paymentStatus,
            Price price, Instant createdAt, String paymentUrl) {
        this.paymentId = paymentId;
        this.userEmail = userEmail;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.paymentStatus = paymentStatus;
        this.price = price;
        this.createdAt = createdAt;
        this.paymentUrl = paymentUrl;
    }

    public UUID getPaymentId() {
        return paymentId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public Price getPrice() {
        return price;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public String getPaymentUrl() {
        return paymentUrl;
    }
}
