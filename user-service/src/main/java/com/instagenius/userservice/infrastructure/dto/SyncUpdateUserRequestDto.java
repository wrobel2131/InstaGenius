package com.instagenius.userservice.infrastructure.dto;

public record SyncUpdateUserRequestDto(String id, String email, String username, String firstName, String lastName,
                                       boolean emailVerified, boolean enabled) {
}
