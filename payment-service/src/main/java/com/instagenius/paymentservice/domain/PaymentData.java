package com.instagenius.paymentservice.domain;

import java.util.Map;

public record PaymentData(String paymentGatewayPaymentId,
                          String eventType, Map<String, String> paymentGatewayPaymentMetadata,
                          String paymentGatewayPaymentMethodId, String latestCharge, String cancellationReason) {
}