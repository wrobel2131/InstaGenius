package com.instagenius.orderservice.domain;

import java.util.UUID;

public record CancelOrder(UUID paymentId) {
}
