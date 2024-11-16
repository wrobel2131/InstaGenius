package com.instagenius.paymentservice.domain;

import java.util.Map;

public record PaymentData(String paymentGatewayPaymentId, Map<String, String> paymentGatewayPaymentMetadata,
                          String paymentGatewayPaymentMethodId, String latestCharge) {
}