package com.instagenius.postmanagementservice.infrastructure.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateDescriptionDto(@NotBlank(message = "User prompt is required!") String userPrompt, @NotNull(message = "Model is required!") String model) {
}
