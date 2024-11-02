package com.instagenius.postgenerationservice.infrastructure.dto;

import jakarta.validation.constraints.NotBlank;

public record DescriptionGenerationOptionsDto(@NotBlank(message = "User prompt is required!") String userPrompt, @NotBlank(message = "Model is required!") String model) {
}
