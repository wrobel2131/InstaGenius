package com.instagenius.productmanagementservice.infrastructure.dto;

import java.util.List;
import java.util.UUID;

public record ProductIdsRequestDto(List<UUID> productIds) {
}
