package com.instagenius.notificationservice.application;

import com.instagenius.notificationservice.domain.OrderEvent;
import com.instagenius.notificationservice.domain.PaymentEvent;

public interface EmailSenderPort {
    void sendOrderRelatedEmail(OrderEvent orderEvent);
    void sendPaymentRelatedEmail(PaymentEvent paymentEvent);
}
