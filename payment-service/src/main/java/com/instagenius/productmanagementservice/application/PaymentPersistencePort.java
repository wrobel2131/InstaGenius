package com.instagenius.productmanagementservice.application;

import com.instagenius.productmanagementservice.domain.Payment;

import java.util.UUID;

public interface PaymentPersistencePort {
    Payment save(Payment payment);
    Payment findPaymentByUserIdAndOrderId(UUID userId, UUID orderId);
}
