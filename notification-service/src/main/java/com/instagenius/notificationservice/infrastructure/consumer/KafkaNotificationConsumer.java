package com.instagenius.notificationservice.infrastructure.consumer;

import com.instagenius.notificationservice.application.NotificationUseCase;
import com.instagenius.notificationservice.domain.OrderEvent;
import com.instagenius.notificationservice.domain.PaymentEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaNotificationConsumer {
    private final NotificationUseCase notificationUseCase;

    @KafkaListener(topics = "order-events", groupId = "notification-service")
    public void consumeOrderEvent(OrderEvent orderEvent) {
        notificationUseCase.processOrderRelatedNotification(orderEvent);
        System.out.println("Processed order related event");
    }

    @KafkaListener(topics = "payment-events", groupId = "notification-service")
    public void consumePaymentEvent(PaymentEvent paymentEvent) {
        notificationUseCase.processPaymentRelatedNotification(paymentEvent);
        System.out.println("Processed payment related event");
    }
}
