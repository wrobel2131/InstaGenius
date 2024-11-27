package com.instagenius.orderservice.infrastructure.adapter;

import com.instagenius.orderservice.application.OrderEventProducerPort;
import com.instagenius.orderservice.domain.OrderEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderEventProducerAdapter implements OrderEventProducerPort {
    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    @Value("${order-service.kafka.topic}")
    private String orderEventsTopic; //order-events

    public void publishOrderEvent(OrderEvent orderEvent) {
        kafkaTemplate.send(orderEventsTopic, orderEvent);
    }
}
