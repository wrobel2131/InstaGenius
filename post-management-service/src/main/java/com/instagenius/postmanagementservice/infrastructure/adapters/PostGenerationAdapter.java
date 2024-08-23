package com.instagenius.postmanagementservice.infrastructure.adapters;

import com.instagenius.postmanagementservice.infrastructure.dto.CreateDescriptionDto;
import com.instagenius.postmanagementservice.infrastructure.dto.GeneratedDescriptionDto;
import com.instagenius.postmanagementservice.infrastructure.exception.GenerationException;
import com.instagenius.postmanagementservice.infrastructure.mapper.DescriptionGenerationOptionsMapper;
import com.instagenius.postmanagementservice.infrastructure.mapper.GeneratedDescriptionMapper;
import com.instagenius.postmanagementservice.infrastructure.mapper.GeneratedImageMapper;
import com.instagenius.postmanagementservice.infrastructure.mapper.ImageGenerationOptionsMapper;
import com.instagenius.postmanagementservice.infrastructure.rest.PostGenerationClient;
import com.instagenius.postmanagementservice.application.PostGenerationPort;
import com.instagenius.postmanagementservice.domain.DescriptionGenerationOptions;
import com.instagenius.postmanagementservice.domain.GeneratedDescription;
import com.instagenius.postmanagementservice.domain.GeneratedImage;
import com.instagenius.postmanagementservice.domain.ImageGenerationOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostGenerationAdapter implements PostGenerationPort {
    private final PostGenerationClient postGenerationClient;
    private static final DescriptionGenerationOptionsMapper descriptionGenerationOptionsMapper = DescriptionGenerationOptionsMapper.INSTANCE;
    private static final GeneratedDescriptionMapper generatedDescriptionMapper = GeneratedDescriptionMapper.INSTANCE;
    private static final ImageGenerationOptionsMapper imageGenerationOptionsMapper = ImageGenerationOptionsMapper.INSTANCE;
    private static final GeneratedImageMapper generatedImageMapper = GeneratedImageMapper.INSTANCE;

    @Override
    public GeneratedDescription generateDescription(DescriptionGenerationOptions descriptionGenerationOptions) {
        return generatedDescriptionMapper.toGeneratedDescription(
                postGenerationClient.generateDescription(
                        descriptionGenerationOptionsMapper.toCreateDescriptionDto(descriptionGenerationOptions)
                ).orElseThrow(() -> new GenerationException("Error while generating description!"))
        );
    }

    @Override
    public GeneratedImage generateImage(ImageGenerationOptions imageGenerationOptions) {
        return generatedImageMapper.toGeneratedImage(
                postGenerationClient.generateImage(
                        imageGenerationOptionsMapper.toCreateImageDto(imageGenerationOptions)
                ).orElseThrow(() -> new GenerationException("Error while generating image!"))
        );
    }
}
