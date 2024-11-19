package com.instagenius.paymentservice.domain;

import java.util.Map;
import java.util.UUID;

public record Product(UUID id, String name, String description, String type, Price price,  Map<String, Object> paymentGatewayProductParams, Map<String, Object> attributes) {
}
