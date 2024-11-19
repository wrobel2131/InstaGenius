package com.instagenius.paymentservice.application;

import com.instagenius.paymentservice.domain.PaymentData;

public interface AsyncPaymentProcessPort {
    void processPaymentData(PaymentData paymentData);
}
