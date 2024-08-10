package com.instagenius.postmanagementservice.infrastructure.dto;

public record PostResponseDto(Long id, String description, String b64Image, String title) {
}
