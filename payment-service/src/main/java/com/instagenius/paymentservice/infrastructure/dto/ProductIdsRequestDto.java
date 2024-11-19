package com.instagenius.paymentservice.infrastructure.dto;

import java.util.List;
import java.util.UUID;

public record ProductIdsRequestDto(List<UUID> productIds) {
}
