package com.instagenius.paymentservice.domain;

import java.util.UUID;

public record OrderedProduct(UUID productId, int quantity) {
}
