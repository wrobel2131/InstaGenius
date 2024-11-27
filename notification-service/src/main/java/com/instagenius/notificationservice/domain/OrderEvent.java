package com.instagenius.notificationservice.domain;

import java.time.Instant;
import java.util.List;

public class OrderEvent {
    private final String referenceId;
    private final String userEmail;
    private final String userFirstName;
    private final String userLastName;
    private final OrderStatus orderStatus;
    private final List<Product> products;
    private final Price totalPrice;
    private final Instant createdAt;
    private final String additionalInfo;

    public OrderEvent(String referenceId, String userEmail, String userFirstName, String userLastName, OrderStatus orderStatus,
                      List<Product> products, Price totalPrice, Instant createdAt, String additionalInfo) {
        this.referenceId = referenceId;
        this.userEmail = userEmail;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.orderStatus = orderStatus;
        this.products = products;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
        this.additionalInfo = additionalInfo;
    }

    public List<Product> getProducts() {
        return products;
    }

    public Price getTotalPrice() {
        return totalPrice;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public String getReferenceId() {
        return referenceId;
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

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }
}
