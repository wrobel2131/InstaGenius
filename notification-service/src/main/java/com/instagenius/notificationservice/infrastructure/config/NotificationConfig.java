package com.instagenius.notificationservice.infrastructure.config;

import com.instagenius.notificationservice.application.EmailSenderPort;
import com.instagenius.notificationservice.application.OrderNotificationPersistencePort;
import com.instagenius.notificationservice.application.NotificationUseCase;
import com.instagenius.notificationservice.application.PaymentNotificationPersistencePort;
import com.instagenius.notificationservice.domain.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class NotificationConfig {
    private final OrderNotificationPersistencePort orderNotificationPersistencePort;
    private final PaymentNotificationPersistencePort paymentNotificationPersistencePort;
    private final EmailSenderPort emailSenderPort;


    @Bean
    public NotificationUseCase notificationUseCase() {
        return new NotificationService(orderNotificationPersistencePort, paymentNotificationPersistencePort, emailSenderPort);
    }
}
