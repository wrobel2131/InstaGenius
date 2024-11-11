package com.instagenius.orderservice.domain;

import java.util.UUID;

public record CompleteOrder(UUID paymentId) {
}
