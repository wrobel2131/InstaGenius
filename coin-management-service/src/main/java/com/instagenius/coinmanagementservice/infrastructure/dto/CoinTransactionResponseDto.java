package com.instagenius.coinmanagementservice.infrastructure.dto;

import java.time.LocalDateTime;

public record CoinTransactionResponseDto(int amount, LocalDateTime createdAt, String type) {
}
