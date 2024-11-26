package com.instagenius.userservice.infrastructure.dto;

public record SyncUpdateUserRequestDto(String email, String username, String firstName, String lastName,
                                       Boolean emailVerified,
                                       Boolean enabled) {
}
