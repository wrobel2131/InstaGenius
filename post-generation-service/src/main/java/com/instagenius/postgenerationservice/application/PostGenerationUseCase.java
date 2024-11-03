package com.instagenius.postgenerationservice.application;

import com.instagenius.postgenerationservice.domain.*;

public interface PostGenerationUseCase {
    GeneratedDescription generateDescription(DescriptionGenerationOptions descriptionGenerationOptions);
    GeneratedImage generateImage(ImageGenerationOptions imageGenerationOptions);
    int calculateGenerationCost(PostGenerationOptions postGenerationOptions);
}
