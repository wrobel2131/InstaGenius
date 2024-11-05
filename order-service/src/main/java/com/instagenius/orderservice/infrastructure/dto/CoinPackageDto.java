package com.instagenius.orderservice.infrastructure.dto;

public record CoinPackageDto(LonString name, int coins, PriceDto price) {
}
