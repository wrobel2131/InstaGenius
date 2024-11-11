package com.instagenius.orderservice.infrastructure.dto;

import java.math.BigDecimal;

public record PriceDto(BigDecimal price, String currency) {
}
