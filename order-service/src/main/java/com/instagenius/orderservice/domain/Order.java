package com.instagenius.orderservice.domain;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class Order {
    private UUID id;
    private UUID userId;
    private OrderStatus status;
    private List<OrderItem> items;
    private Price totalPrice;
    private Instant createdAt;
    private Instant updatedAt;


}
