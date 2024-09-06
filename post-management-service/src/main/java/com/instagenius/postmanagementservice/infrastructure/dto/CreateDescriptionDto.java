package com.instagenius.postmanagementservice.infrastructure.dto;

import jakarta.validation.constraints.NotNull;

public record CreateDescriptionDto(@NotNull String userPrompt, @NotNull String model) {
}
