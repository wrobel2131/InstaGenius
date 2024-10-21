package com.instagenius.coinmanagementservice.infrastructure.dto;

import java.util.List;

public record CoinTransactionsResponseDto(List<CoinTransactionResponseDto> coinTransactions) {
}
