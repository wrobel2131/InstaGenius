package com.instagenius.notificationservice.domain;

import com.instagenius.notificationservice.application.EmailSenderPort;
import com.instagenius.notificationservice.application.OrderNotificationPersistencePort;
import com.instagenius.notificationservice.application.NotificationUseCase;
import com.instagenius.notificationservice.application.PaymentNotificationPersistencePort;

public class NotificationService implements NotificationUseCase {
    private final OrderNotificationPersistencePort orderNotificationPersistencePort;
    private final PaymentNotificationPersistencePort paymentNotificationPersistencePort;
    private final EmailSenderPort emailSenderPort;

    public NotificationService(OrderNotificationPersistencePort orderNotificationPersistencePort, PaymentNotificationPersistencePort paymentNotificationPersistencePort,
                               EmailSenderPort emailSenderPort) {
        this.orderNotificationPersistencePort = orderNotificationPersistencePort;
        this.paymentNotificationPersistencePort = paymentNotificationPersistencePort;
        this.emailSenderPort = emailSenderPort;
    }


    @Override
    public void processOrderRelatedNotification(OrderNotification orderNotification) {

    }

    @Override
    public void processPaymentRelatedNotification(PaymentNotification paymentNotification) {

    }
}
