package com.instagenius.orderservice.domain;

import java.util.UUID;

public record OrderItem(UUID id, Product product, int quantity, Price unitPrice, Price totalPrice ) {
}
