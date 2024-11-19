package com.instagenius.paymentservice.application;

import com.instagenius.paymentservice.domain.PaymentData;
import com.instagenius.paymentservice.domain.Payment;
import com.instagenius.paymentservice.domain.PaymentStatus;
import com.instagenius.paymentservice.domain.Product;

import java.util.Map;

public interface PaymentGatewayPort {
    Payment createPaymentSession(Payment payment, Map<Product, Integer> products);
    PaymentData getPaymentDataFromEvent(String payload, String header, PaymentStatus paymentStatus);
}
