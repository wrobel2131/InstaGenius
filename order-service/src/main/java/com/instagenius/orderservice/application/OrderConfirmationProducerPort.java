package com.instagenius.orderservice.application;

import com.instagenius.orderservice.domain.Order;
import com.instagenius.orderservice.domain.OrderConfirmation;

public interface OrderConfirmationProducerPort {
    void sendOrderConfirmation(OrderConfirmation orderConfirmation);
}
