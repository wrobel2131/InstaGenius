package com.instagenius.postmanagementservice.infrastructure.adapters;

import com.instagenius.postmanagementservice.domain.*;
import com.instagenius.postmanagementservice.infrastructure.dto.CreatePostOptionsDto;
import com.instagenius.postmanagementservice.infrastructure.exception.PostGenerationException;
import com.instagenius.postmanagementservice.infrastructure.mapper.*;
import com.instagenius.postmanagementservice.infrastructure.rest.PostGenerationClient;
import com.instagenius.postmanagementservice.application.PostGenerationPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class PostGenerationAdapter implements PostGenerationPort {
    private final PostGenerationClient postGenerationClient;
    private static final DescriptionGenerationOptionsMapper descriptionGenerationOptionsMapper = DescriptionGenerationOptionsMapper.INSTANCE;
    private static final GeneratedDescriptionMapper generatedDescriptionMapper = GeneratedDescriptionMapper.INSTANCE;
    private static final ImageGenerationOptionsMapper imageGenerationOptionsMapper = ImageGenerationOptionsMapper.INSTANCE;
    private static final GeneratedImageMapper generatedImageMapper = GeneratedImageMapper.INSTANCE;
    private static final GenerationCostMapper generationCostMapper = GenerationCostMapper.INSTANCE;

    @Override
    public GeneratedDescription generateDescription(DescriptionGenerationOptions descriptionGenerationOptions) {
        return generatedDescriptionMapper.toGeneratedDescription(
                postGenerationClient.generateDescription(
                        descriptionGenerationOptionsMapper.toCreateDescriptionDto(descriptionGenerationOptions)
                ).orElseThrow(() -> new PostGenerationException("Error while generating description!"))
        );
    }

    @Override
    public GeneratedImage generateImage(ImageGenerationOptions imageGenerationOptions) {
        return generatedImageMapper.toGeneratedImage(
                postGenerationClient.generateImage(
                        imageGenerationOptionsMapper.toCreateImageDto(imageGenerationOptions)
                ).orElseThrow(() -> new PostGenerationException("Error while generating image!"))
        );
    }

    @Override
    public GenerationCost calculateGenerationCost(DescriptionGenerationOptions descriptionGenerationOptions, ImageGenerationOptions imageGenerationOptions) {
        return generationCostMapper.toGenerationCost(
                postGenerationClient.calculateGenerationCost(new CreatePostOptionsDto(
                        descriptionGenerationOptionsMapper.toCreateDescriptionDto(descriptionGenerationOptions),
                        imageGenerationOptionsMapper.toCreateImageDto(imageGenerationOptions)
                )).orElseThrow(() -> new PostGenerationException("Error while calculating generation cost!"))
        );
    }

}
