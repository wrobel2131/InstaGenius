package com.instagenius.user.dto;

public record UpdateUserDto(String username, String email, String firstName, String lastName,
                      boolean emailVerified, boolean enabled) {
}

