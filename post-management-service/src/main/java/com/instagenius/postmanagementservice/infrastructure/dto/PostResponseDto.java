package com.instagenius.postmanagementservice.infrastructure.dto;

import java.util.UUID;

public record PostResponseDto(UUID id, String description, String b64Image, String title) {
}
