package com.instagenius.notificationservice.application;

import com.instagenius.notificationservice.domain.OrderEvent;
import com.instagenius.notificationservice.domain.PaymentEvent;

public interface NotificationUseCase {
    void processOrderRelatedNotification(OrderEvent orderEvent);
    void processPaymentRelatedNotification(PaymentEvent paymentEvent);
}
