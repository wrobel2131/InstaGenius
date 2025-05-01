package com.instagenius.userservice.domain;

public record SyncUpdateUser(String email, String username, String firstName, String lastName, boolean emailVerified,
                             boolean enabled) {
}
