package com.instagenius.postmanagementservice.infrastructure.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreatePostRequestDto(@NotNull(message = "Options to generate descriptions are required!") CreateDescriptionDto descriptionOptions, @NotNull(message = "Options to generate image are required!") CreateImageDto imageOptions, @NotBlank(message = "Post title is required!") String title) {
}
