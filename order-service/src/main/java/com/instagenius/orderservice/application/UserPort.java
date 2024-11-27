package com.instagenius.orderservice.application;

import com.instagenius.orderservice.domain.UserProfile;

import java.util.UUID;

public interface UserPort {
    UserProfile getUserProfile(UUID userId);
}
