package com.instagenius.paymentservice.application;

import com.instagenius.paymentservice.domain.UserProfile;

import java.util.UUID;

public interface UserPort {
    UserProfile getUserProfile(UUID userId);
}
