package com.instagenius.postgenerationservice.domain;

public record ImageGenerationOptions(String userPrompt, DALLEModel model, ImageQuality quality, ImageSize size,
                                     ImageStyle style) {
}
