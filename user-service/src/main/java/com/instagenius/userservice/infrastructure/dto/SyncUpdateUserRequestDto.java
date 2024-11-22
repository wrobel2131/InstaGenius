package com.instagenius.userservice.infrastructure.dto;

public record UpdateUserRequestDto(String email, String firstName, String lastName, Boolean emailVerified, Boolean enabled) {
}
