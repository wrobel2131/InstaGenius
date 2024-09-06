package com.instagenius.postmanagementservice.application;

import com.instagenius.postmanagementservice.domain.DescriptionGenerationOptions;
import com.instagenius.postmanagementservice.domain.GeneratedDescription;
import com.instagenius.postmanagementservice.domain.GeneratedImage;
import com.instagenius.postmanagementservice.domain.ImageGenerationOptions;

public interface PostGenerationPort {
    GeneratedDescription generateDescription(DescriptionGenerationOptions descriptionGenerationOptions);

    GeneratedImage generateImage(ImageGenerationOptions imageGenerationOptions);
}
