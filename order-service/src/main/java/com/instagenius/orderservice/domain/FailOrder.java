package com.instagenius.orderservice.domain;

import java.util.UUID;

public record FailOrder(UUID paymentId) {
}
