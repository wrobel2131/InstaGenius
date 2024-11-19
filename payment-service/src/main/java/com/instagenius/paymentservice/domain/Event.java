package com.instagenius.paymentservice.domain;

import java.time.Instant;
import java.util.UUID;

public record Event(UUID id, String eventId, String type, boolean livemode, Instant createdAt, int version) {

    public Event(UUID id, String eventId, String type, boolean livemode, Long createdAt, int version) {
        this(id, eventId, type, livemode, Instant.ofEpochSecond(createdAt), version);
    }
}
