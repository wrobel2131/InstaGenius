package com.instagenius.postgenerationservice.domain;

import com.instagenius.postgenerationservice.application.PostGenerationOutputPort;
import com.instagenius.postgenerationservice.application.PostGenerationUseCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class PostGenerationService implements PostGenerationUseCase {
    private final PostGenerationOutputPort postGenerationOutputPort;

    @Override
    public GeneratedDescription generateDescription(DescriptionGenerationOptions descriptionGenerationOptions) {
        return postGenerationOutputPort.generateDescription(descriptionGenerationOptions);
    }

    @Override
    public GeneratedImage generateImage(ImageGenerationOptions imageGenerationOptions) {
        return postGenerationOutputPort.generateImage(imageGenerationOptions);
    }
}
