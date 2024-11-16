package com.instagenius.paymentservice.application;

import com.instagenius.paymentservice.domain.Payment;

import java.util.UUID;

public interface PaymentPersistencePort {
    Payment save(Payment payment);
    Payment getPaymentById(UUID id);
}
