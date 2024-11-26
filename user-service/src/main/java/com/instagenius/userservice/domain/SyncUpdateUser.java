package com.instagenius.userservice.domain;

public record SyncUpdateUser(String email, String username, String firstName, String lastName, Boolean emailVerified,
                             Boolean enabled) {
}
