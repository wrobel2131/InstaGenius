package com.instagenius.postmanagementservice.infrastructure.dto;

import jakarta.validation.constraints.NotNull;

public record CreatePostRequestDto(@NotNull CreateDescriptionDto descriptionOptions, @NotNull CreateImageDto imageOptions, String title) {
}
