package com.instagenius.notificationservice.application;

import com.instagenius.notificationservice.domain.PaymentNotification;

public interface PaymentNotificationPersistencePort {
    PaymentNotification save(PaymentNotification paymentNotification);
}
