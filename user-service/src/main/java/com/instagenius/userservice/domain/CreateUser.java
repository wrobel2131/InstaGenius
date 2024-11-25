package com.instagenius.userservice.domain;

public record CreateUser(String id, String username, String email, String firstName, String lastName,
                         boolean emailVerified, boolean enabled, Long createdAt) {
}
