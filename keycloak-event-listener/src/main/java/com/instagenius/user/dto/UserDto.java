package com.instagenius.user.dto;

public record UserDto(String id, String username, String email, String firstName, String lastName,
                      boolean emailVerified, boolean enabled, Long createdAt) {
}
