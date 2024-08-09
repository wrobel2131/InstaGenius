package com.instagenius.postmanagementservice.domain;

import java.util.UUID;

public record Post(Long id, UUID userId, String title, String imageUrl, String description) {
}
