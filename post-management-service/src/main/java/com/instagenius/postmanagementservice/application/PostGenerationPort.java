package com.instagenius.postmanagementservice.application;

import com.instagenius.postmanagementservice.domain.*;

public interface PostGenerationPort {
    GeneratedDescription generateDescription(DescriptionGenerationOptions descriptionGenerationOptions);
    GeneratedImage generateImage(ImageGenerationOptions imageGenerationOptions);
    GenerationCost calculateGenerationCost(DescriptionGenerationOptions descriptionGenerationOptions, ImageGenerationOptions imageGenerationOptions);
}
