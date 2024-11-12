package com.instagenius.productmanagementservice.infrastructure.dto;

import java.util.List;

public record ProductsResponse(List<ProductResponse> products) {
}
