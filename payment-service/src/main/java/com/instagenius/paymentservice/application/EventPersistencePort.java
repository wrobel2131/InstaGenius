package com.instagenius.paymentservice.application;

import com.instagenius.paymentservice.domain.Event;

public interface EventPersistencePort {
    Event saveEvent(Event event);
    boolean existsByEventId(String eventId);
}
