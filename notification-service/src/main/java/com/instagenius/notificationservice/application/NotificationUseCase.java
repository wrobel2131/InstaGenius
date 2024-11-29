package com.instagenius.notificationservice.application;

import com.instagenius.notificationservice.domain.OrderEvent;
import com.instagenius.notificationservice.domain.OrderNotification;
import com.instagenius.notificationservice.domain.PaymentEvent;
import com.instagenius.notificationservice.domain.PaymentNotification;

public interface NotificationUseCase {
    void processOrderRelatedNotification(OrderEvent orderEvent);
    void processPaymentRelatedNotification(PaymentEvent paymentEvent);
}
