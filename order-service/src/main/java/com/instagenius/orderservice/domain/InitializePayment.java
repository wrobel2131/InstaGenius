package com.instagenius.orderservice.domain;

import java.util.UUID;

public record InitializePayment(UUID orderId, Price price) {
}
