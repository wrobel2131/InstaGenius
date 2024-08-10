package com.instagenius.postmanagementservice.infrastructure.dto;

import jakarta.validation.constraints.NotNull;

public record CreatePostDto(@NotNull CreateDescriptionDto descriptionOptions, @NotNull CreateImageDto imageOptions, String title) {
}
