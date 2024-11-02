package com.instagenius.postgenerationservice.infrastructure.dto;

import jakarta.validation.constraints.NotBlank;

public record ImageGenerationOptionsDto(@NotBlank(message = "User prompt is required!") String userPrompt, @NotBlank(message = "Model is required!") String model, String quality,
                                        @NotBlank(message = "Width of the image is required!") String width, @NotBlank(message = "Height of the image is required!") String height, String style) {
}
