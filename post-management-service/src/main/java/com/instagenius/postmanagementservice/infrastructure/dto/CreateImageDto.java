package com.instagenius.postmanagementservice.infrastructure.dto;

import jakarta.validation.constraints.NotNull;

public record CreateImageDto(@NotNull String userPrompt, @NotNull String model, String quality,
                             @NotNull String width, @NotNull String height, String style) {
}