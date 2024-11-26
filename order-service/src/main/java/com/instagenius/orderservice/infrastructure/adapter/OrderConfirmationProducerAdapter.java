package com.instagenius.orderservice.infrastructure.adapter;

import com.instagenius.orderservice.application.OrderConfirmationProducerPort;
import com.instagenius.orderservice.domain.Order;
import com.instagenius.orderservice.domain.OrderConfirmation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderConfirmationProducerAdapter implements OrderConfirmationProducerPort {
    private final KafkaTemplate<String, OrderConfirmation> kafkaTemplate;

    @Value("${order-service.kafka.topic}")
    private String topic;

    public void sendOrderConfirmation(OrderConfirmation orderConfirmation) {
        System.out.println("Sending order confirmation: " + orderConfirmation);
        kafkaTemplate.send(topic, orderConfirmation);
    }
}
