package com.instagenius.paymentservice.domain;

import java.util.UUID;

public record CreatedPayment(UUID paymentId, String paymentGatewaySessionId) {
}
