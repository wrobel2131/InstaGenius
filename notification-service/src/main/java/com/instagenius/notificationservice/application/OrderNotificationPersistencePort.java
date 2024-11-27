package com.instagenius.notificationservice.application;

import com.instagenius.notificationservice.domain.OrderNotification;

public interface OrderNotificationPersistencePort {
    OrderNotification save(OrderNotification orderNotification);
}
