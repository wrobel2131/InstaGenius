package com.instagenius.coinmanagementservice.domain;

import java.util.UUID;

public record CoinPackage(UUID id, String name, String description, int coinAmount, Price price, boolean isActive) {
}
