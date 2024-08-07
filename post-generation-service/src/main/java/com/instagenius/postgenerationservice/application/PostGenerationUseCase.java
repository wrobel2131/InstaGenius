package com.instagenius.postgenerationservice.application;

import com.instagenius.postgenerationservice.architecture.dto.GeneratedDescriptionResponseDto;
import com.instagenius.postgenerationservice.architecture.dto.GeneratedImageResponseDto;
import com.instagenius.postgenerationservice.domain.DescriptionGenerationOptions;
import com.instagenius.postgenerationservice.domain.GeneratedDescription;
import com.instagenius.postgenerationservice.domain.GeneratedImage;
import com.instagenius.postgenerationservice.domain.ImageGenerationOptions;

public interface PostGenerationUseCase {
    GeneratedDescription generateDescription(DescriptionGenerationOptions descriptionGenerationOptions);
    GeneratedImage generateImage(ImageGenerationOptions imageGenerationOptions);
}
