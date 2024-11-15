package com.instagenius.productmanagementservice.application;

import com.instagenius.productmanagementservice.domain.CreatedPayment;

import java.math.BigDecimal;
import java.util.UUID;

public interface PaymentUseCase {
    CreatedPayment initializePayment(UUID userId, UUID orderId, BigDecimal price, String currency);
}
