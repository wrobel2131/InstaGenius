package com.instagenius.postgenerationservice.domain;

import com.instagenius.postgenerationservice.application.PostGenerationOutputPort;
import com.instagenius.postgenerationservice.application.PostGenerationUseCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PostGenerationService implements PostGenerationUseCase {
    private final PostGenerationOutputPort postGenerationOutputPort;

    @Override
    public GeneratedDescription generateDescription(DescriptionGenerationOptions descriptionGenerationOptions) {
        return postGenerationOutputPort.generateDescription(descriptionGenerationOptions); //TODO map it to dto
    }

    @Override
    public GeneratedImage generateImage(ImageGenerationOptions imageGenerationOptions) {
        return postGenerationOutputPort.generateImage(imageGenerationOptions); //TODO map it to dto
    }
}
