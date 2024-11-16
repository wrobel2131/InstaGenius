package com.instagenius.paymentservice.domain;

import java.util.UUID;

public record InitializedPayment(UUID paymentId, String paymentCheckoutSessionId) {
}
