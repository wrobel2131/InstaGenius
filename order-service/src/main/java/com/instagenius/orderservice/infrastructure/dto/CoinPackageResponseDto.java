package com.instagenius.orderservice.infrastructure.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record CoinPackageResponseDto(UUID id, String name, String description, int coins, BigDecimal price, String currency, String type) {
}
