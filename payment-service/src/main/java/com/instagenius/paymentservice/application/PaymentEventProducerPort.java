package com.instagenius.paymentservice.application;

import com.instagenius.paymentservice.domain.PaymentEvent;

public interface PaymentEventProducerPort {
    void publishPaymentEvent(PaymentEvent paymentEvent);
}