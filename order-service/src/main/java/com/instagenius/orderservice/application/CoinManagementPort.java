package com.instagenius.orderservice.application;

import com.instagenius.orderservice.domain.AddCoins;

public interface CoinManagementPort {
    void addCoins(AddCoins addCoins);
}
