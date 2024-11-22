package com.instagenius.userservice.application;

import com.instagenius.userservice.domain.UpdateUser;
import com.instagenius.userservice.domain.User;

import java.util.UUID;

public interface UserUseCase {
    User findUserById(UUID id);
    User updateUser(UUID id, UpdateUser updateUser);
}
