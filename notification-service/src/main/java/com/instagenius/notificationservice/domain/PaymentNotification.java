package com.instagenius.notificationservice.domain;

import java.time.Instant;
import java.util.UUID;

public record PaymentNotification(UUID id, UUID paymentId, String userEmail, Instant createdAt, PaymentStatus paymentStatus, int version) {
}
