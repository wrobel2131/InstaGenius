package com.instagenius.orderservice.infrastructure.dto;

import java.util.UUID;

public record UserResponseDto(UUID id, String username, String email, String firstName, String lastName) {
}
