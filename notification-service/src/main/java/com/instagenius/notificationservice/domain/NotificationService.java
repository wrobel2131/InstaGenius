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
    public void processOrderRelatedNotification(OrderEvent orderEvent) {
        orderNotificationPersistencePort.save(new OrderNotification(null, orderEvent.getReferenceId(),
                orderEvent.getUserEmail(), null, orderEvent.getOrderStatus(), 0));

        emailSenderPort.sendOrderRelatedEmail(orderEvent);
        System.out.println("Saved order notification and sent ");
    }

    @Override
    public void processPaymentRelatedNotification(PaymentEvent paymentEvent) {
        paymentNotificationPersistencePort.save(new PaymentNotification()); //TODO create fields in PaymentNotification domain object
        emailSenderPort.sendPaymentRelatedEmail(paymentEvent);

    }
}
