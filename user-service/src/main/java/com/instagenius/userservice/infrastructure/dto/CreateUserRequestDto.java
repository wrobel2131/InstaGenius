package com.instagenius.userservice.infrastructure.dto;

import jakarta.validation.constraints.NotNull;

public record CreateUserRequestDto(@NotNull(message = "Id is required!") String id, @NotNull(message = "Realm Id is required!") String realmId, @NotNull(message = "Username is required!") String username, @NotNull(message = "Email is required!") String email, String firstName, String lastName,
                                   boolean emailVerified, boolean enabled,@NotNull(message = "Created time is required!") Long createdAt) {
}
