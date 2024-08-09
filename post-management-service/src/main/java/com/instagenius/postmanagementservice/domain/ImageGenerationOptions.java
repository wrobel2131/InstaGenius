package com.instagenius.postmanagementservice.domain;


public record ImageGenerationOptions(String userPrompt, String model, String quality,
                                      String width, String height, String style) {
}
