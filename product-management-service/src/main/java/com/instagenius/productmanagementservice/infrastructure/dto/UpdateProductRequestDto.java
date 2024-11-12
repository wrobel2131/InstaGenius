package com.instagenius.productmanagementservice.infrastructure.dto;

import java.math.BigDecimal;
import java.util.Map;

public record UpdateProductRequestDto(String name, String description, String type, BigDecimal price, String currency, Map<String, Object> attributes, Boolean isActive) {
}
