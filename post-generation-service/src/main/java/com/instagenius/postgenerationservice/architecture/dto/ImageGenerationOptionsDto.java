package com.instagenius.postgenerationservice.architecture.dto;

import jakarta.validation.constraints.NotNull;

public record ImageGenerationOptionsDto(@NotNull String userPrompt, @NotNull String model, String quality, @NotNull String width, @NotNull String height, String style) {
}
