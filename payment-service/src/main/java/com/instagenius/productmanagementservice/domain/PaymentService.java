package com.instagenius.productmanagementservice.domain;

import com.instagenius.productmanagementservice.application.PaymentPersistencePort;
import com.instagenius.productmanagementservice.application.PaymentUseCase;

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
