package com.instagenius.orderservice.infrastructure.adapter;

import com.instagenius.orderservice.application.CoinManagementPort;
import com.instagenius.orderservice.application.OrderCompletionHandler;
import com.instagenius.orderservice.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CoinPackageOrderCompletionHandler implements OrderCompletionHandler {
    private final CoinManagementPort coinManagementPort;
    @Override
    public ProductType getProductType() {
        return ProductType.COIN_PACKAGE;
    }

    @Override
    public void handleOrderItemCompletion(UUID userId, OrderItem orderItem) {
        System.out.println("Handling order completion");
        int coins = calculateCoinAmountFromOrder(orderItem);

        coinManagementPort.addCoins(userId, new AddCoins(coins, "PURCHASE"));
    }

    private int calculateCoinAmountFromOrder(OrderItem orderItem) {
        System.out.println("Calculating coin amount");
        return orderItem.getQuantity() * (Integer) orderItem.getProduct().attributes().getOrDefault("coins", 0);
    }
}
