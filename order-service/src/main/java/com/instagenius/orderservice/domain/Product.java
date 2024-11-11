package com.instagenius.orderservice.domain;

import java.util.Map;
import java.util.UUID;

public record Product(UUID id, String name, String description, ProductType type, Price price, Map<String, Object> attributes) {
}
