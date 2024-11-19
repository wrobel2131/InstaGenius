package com.instagenius.paymentservice.application;

import com.instagenius.paymentservice.domain.CancelOrder;
import com.instagenius.paymentservice.domain.CompleteOrder;
import com.instagenius.paymentservice.domain.FailOrder;

import java.util.UUID;

public interface OrderPort {
    void completeOrder(UUID orderId, CompleteOrder completeOrder);
    void cancelOrder(UUID orderId, CancelOrder cancelOrder);
    void failOrder(UUID orderId, FailOrder failOrder);
}
