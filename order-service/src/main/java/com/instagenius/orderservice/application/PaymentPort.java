package com.instagenius.orderservice.application;

import com.instagenius.orderservice.domain.CreatedPayment;
import com.instagenius.orderservice.domain.InitializePayment;

public interface PaymentPort {
    CreatedPayment initializePaymentSession(InitializePayment initializePayment);
}
