package com.instagenius.userservice.infrastructure.dto;

public record UpdateUserRequestDto(String email, String username, String firstName, String lastName) {
}
