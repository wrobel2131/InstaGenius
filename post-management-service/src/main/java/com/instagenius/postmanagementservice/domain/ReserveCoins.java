package com.instagenius.postmanagementservice.domain;

import java.util.UUID;

public record ReserveCoins(int amount, UUID operationId) {
}
