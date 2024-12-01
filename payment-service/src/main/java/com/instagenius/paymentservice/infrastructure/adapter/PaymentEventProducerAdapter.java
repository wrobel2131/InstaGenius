package com.instagenius.paymentservice.infrastructure.adapter;

import com.instagenius.paymentservice.application.PaymentEventProducerPort;
import com.instagenius.paymentservice.domain.PaymentEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentEventProducerAdapter implements PaymentEventProducerPort {
    private final KafkaTemplate<String, PaymentEvent> kafkaTemplate;

    @Value("${payment-service.kafka.topic}")
    private String paymentEventTopic;

    @Override
    public void publishPaymentEvent(PaymentEvent paymentEvent) {
        System.out.println("Publishing payment event: " + paymentEvent);
        kafkaTemplate.send(paymentEventTopic, paymentEvent);
    }
}