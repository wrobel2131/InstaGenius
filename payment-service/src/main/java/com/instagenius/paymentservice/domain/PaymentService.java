package com.instagenius.paymentservice.domain;

import com.instagenius.paymentservice.application.PaymentPersistencePort;
import com.instagenius.paymentservice.application.PaymentUseCase;

import java.math.BigDecimal;
import java.util.UUID;

public class PaymentService implements PaymentUseCase {
    private final PaymentPersistencePort paymentPersistencePort;

    public PaymentService(PaymentPersistencePort paymentPersistencePort) {
        this.paymentPersistencePort = paymentPersistencePort;
    }

    @Override
    public CreatedPayment initializePayment(UUID userId, UUID orderId, BigDecimal price, String currency) {
        return null;
    }
}
