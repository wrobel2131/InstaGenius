package com.instagenius.productmanagementservice.infrastructure.dto;

import java.math.BigDecimal;
import java.util.Map;

public record UpdateProductRequest(String name, String description, String type, BigDecimal price, String currency, Map<String, Object> attributes, boolean isActive) {
}
