package com.instagenius.orderservice.domain;

import java.util.List;
import java.util.UUID;

public record InitializePayment(UUID orderId, String referenceId, List<ProductsToPay> orderedProducts) {
}
