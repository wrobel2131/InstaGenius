package com.instagenius.paymentservice.domain;

import java.util.UUID;

public record UserProfile(UUID id, String username, String email, String firstName, String lastName) {
}
