package com.instagenius.userservice.infrastructure.dto;

import java.util.UUID;

public record CreatedUserResponseDto(UUID id, String kcUserId, String kcRealmId) {
}
