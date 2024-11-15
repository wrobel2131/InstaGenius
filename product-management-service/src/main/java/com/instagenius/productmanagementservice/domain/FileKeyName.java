package com.instagenius.productmanagementservice.domain;

import java.util.UUID;

public record FileKeyName(String keyName) {
    public FileKeyName() {
        this(createKeyName());
    }

    private static String createKeyName() {
        return  "product-images/" + UUID.randomUUID();
    }
}
