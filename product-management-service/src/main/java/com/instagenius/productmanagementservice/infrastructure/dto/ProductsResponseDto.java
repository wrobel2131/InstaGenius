package com.instagenius.productmanagementservice.infrastructure.dto;

import java.util.List;

public record ProductsResponseDto(List<ProductResponseDto> products) {
}
