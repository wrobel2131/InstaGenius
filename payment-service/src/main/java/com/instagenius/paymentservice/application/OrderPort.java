package com.instagenius.paymentservice.application;

import com.instagenius.paymentservice.domain.CompleteOrder;

import java.util.UUID;

public interface OrderPort {
    void completeOrder(UUID orderId, CompleteOrder completeOrder);
}
