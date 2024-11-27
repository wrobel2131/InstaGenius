package com.instagenius.notificationservice.application;

import com.instagenius.notificationservice.domain.OrderNotification;
import com.instagenius.notificationservice.domain.PaymentNotification;

public interface NotificationUseCase {
    void processOrderRelatedNotification(OrderNotification orderNotification);
    void processPaymentRelatedNotification(PaymentNotification paymentNotification);
}
