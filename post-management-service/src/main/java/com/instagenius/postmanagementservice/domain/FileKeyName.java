package com.instagenius.postmanagementservice.domain;

import java.util.UUID;

public record FileKeyName(String keyName) {
    public FileKeyName(UUID userId) {
        this(createKeyName(userId));
    }

    private static String createKeyName(UUID userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        return userId.toString() + '/' + UUID.randomUUID();
    }
}
