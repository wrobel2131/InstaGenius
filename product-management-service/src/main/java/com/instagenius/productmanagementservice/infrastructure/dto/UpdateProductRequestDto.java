package com.instagenius.productmanagementservice.infrastructure.dto;

import com.instagenius.productmanagementservice.domain.ProductType;

import java.math.BigDecimal;
import java.util.Map;

public record UpdateProductRequestDto(String name, String description, ProductType type, BigDecimal price, String currency, String imageUrl, Map<String, Object> attributes, Boolean isActive) {
}
