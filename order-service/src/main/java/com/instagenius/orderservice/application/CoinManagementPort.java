package com.instagenius.orderservice.application;

import com.instagenius.orderservice.domain.AddCoins;

import java.util.UUID;

public interface CoinManagementPort {
    void addCoins(UUID userId, AddCoins addCoins);
}
