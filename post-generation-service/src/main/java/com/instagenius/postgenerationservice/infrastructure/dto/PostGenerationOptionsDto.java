package com.instagenius.postgenerationservice.infrastructure.dto;

import jakarta.validation.constraints.NotNull;

public record PostGenerationOptionsDto(@NotNull(message = "Description generation options are required!") DescriptionGenerationOptionsDto descriptionGenerationOptions,
                                       @NotNull(message = "Image generation options are required!") ImageGenerationOptionsDto imageGenerationOptions) {
}
