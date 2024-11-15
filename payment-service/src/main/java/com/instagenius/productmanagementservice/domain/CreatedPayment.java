package com.instagenius.productmanagementservice.domain;

import java.util.UUID;

public record CreatedPayment(UUID paymentId, String paymentGatewaySessionId) {
}
