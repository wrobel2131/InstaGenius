package com.instagenius.coinmanagementservice.infrastructure.dto;

import java.time.Instant;


public record CoinTransactionResponseDto(int amount, Instant createdAt, String type) {
}
