package com.instagenius.orderservice.domain;

import java.util.UUID;

public record CreatedPayment(UUID paymentId, String paymentCheckoutSessionId) {
}
