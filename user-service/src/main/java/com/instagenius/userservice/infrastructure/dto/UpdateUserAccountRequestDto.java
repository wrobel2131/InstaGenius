package com.instagenius.userservice.infrastructure.dto;

public record UpdateUserAccountRequestDto(String id, String username, String firstName, String lastName, String email) {
}
