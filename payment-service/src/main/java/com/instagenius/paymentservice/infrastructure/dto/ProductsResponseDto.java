package com.instagenius.paymentservice.infrastructure.dto;


import java.util.List;

public record ProductsResponseDto(List<ProductResponseDto> products) {
}
