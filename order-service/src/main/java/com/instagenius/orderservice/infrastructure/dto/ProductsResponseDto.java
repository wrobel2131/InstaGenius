package com.instagenius.orderservice.infrastructure.dto;

import java.util.List;

public record ProductsResponseDto(List<ProductResponseDto> products) {
}
