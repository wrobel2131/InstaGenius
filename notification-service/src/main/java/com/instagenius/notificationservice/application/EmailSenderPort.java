package com.instagenius.notificationservice.application;

import com.instagenius.notificationservice.domain.OrderEvent;

public interface EmailSenderPort {
    void sendOrderRelatedEmail(OrderEvent orderEvent);
}
