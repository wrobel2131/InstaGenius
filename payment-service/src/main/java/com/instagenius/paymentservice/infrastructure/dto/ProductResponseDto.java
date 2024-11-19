package com.instagenius.paymentservice.infrastructure.dto;


import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

public record ProductResponseDto(UUID id, String name, String description, String type, BigDecimal price,
                                 String currency, Map<String, Object> paymentGatewayProductParams, Map<String, Object> attributes) {
}
