package com.instagenius.orderservice.domain;

import java.util.UUID;

public record ProductsToPay(UUID productId, int quantity) {
}
