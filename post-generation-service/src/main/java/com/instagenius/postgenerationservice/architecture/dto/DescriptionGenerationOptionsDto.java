package com.instagenius.postgenerationservice.architecture.dto;

import jakarta.validation.constraints.NotNull;

public record DescriptionGenerationOptionsDto(@NotNull String userPrompt, @NotNull String model) {
}
