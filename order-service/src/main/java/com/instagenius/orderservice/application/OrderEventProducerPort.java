package com.instagenius.orderservice.application;

import com.instagenius.orderservice.domain.OrderEvent;

public interface OrderEventProducerPort {
    void publishOrderEvent(OrderEvent orderEvent);
}
