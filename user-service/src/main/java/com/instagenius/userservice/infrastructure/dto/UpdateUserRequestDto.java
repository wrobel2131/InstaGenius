package com.instagenius.userservice.infrastructure.dto;

import jakarta.validation.constraints.NotNull;

public record UpdateUserRequestDto(@NotNull(message = "Email is required!") String email,
                                   @NotNull(message = "Username is required!") String username,
                                   @NotNull(message = "First name is required!") String firstName,
                                   @NotNull(message = "Last name is required!") String lastName) {
}
