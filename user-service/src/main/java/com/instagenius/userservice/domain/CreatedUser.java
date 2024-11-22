package com.instagenius.userservice.domain;

import java.util.UUID;

public record CreatedUser(UUID id, String kcUserId, String kcRealmId) {
}
