package com.instagenius.productmanagementservice.infrastructure.dto;

import com.instagenius.productmanagementservice.domain.ProductType;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

public record ProductResponseDto(UUID id, String name, String description, ProductType type, BigDecimal price,
                                 String currency,
                                 Map<String, Object> attributes) {
}
