package com.instagenius.notificationservice.domain;

import java.time.Instant;
import java.util.UUID;

public record OrderNotification(UUID id, String orderReferenceId, String userEmail, Instant createdAt, OrderStatus orderStatus, int version) {
}
