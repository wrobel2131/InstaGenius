package com.instagenius.postgenerationservice.architecture.controller;


import com.instagenius.postgenerationservice.application.PostGenerationUseCase;
import com.instagenius.postgenerationservice.architecture.dto.DescriptionGenerationOptionsDto;
import com.instagenius.postgenerationservice.architecture.dto.GeneratedDescriptionResponseDto;
import com.instagenius.postgenerationservice.architecture.dto.ImageGenerationOptionsDto;
import com.instagenius.postgenerationservice.architecture.dto.GeneratedImageResponseDto;
import com.instagenius.postgenerationservice.architecture.mapper.DescriptionGenerationOptionsMapper;
import com.instagenius.postgenerationservice.architecture.mapper.ImageGenerationOptionsMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
class PostGenerationController {
    private final PostGenerationUseCase postGenerationUseCase;
    private final ImageGenerationOptionsMapper imageGenerationOptionsMapper = ImageGenerationOptionsMapper.INSTANCE;
    private final DescriptionGenerationOptionsMapper descriptionGenerationOptionsMapper =
            DescriptionGenerationOptionsMapper.INSTANCE;


    @PostMapping(value = "/generate-image", consumes = MediaType.APPLICATION_JSON_VALUE, produces =
            MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<GeneratedImageResponseDto> generateImage(
            @Valid @RequestBody ImageGenerationOptionsDto imageGenerationOptionsDto) {
        return ResponseEntity.ok(postGenerationUseCase.generateImage(
                imageGenerationOptionsMapper.toImageGenerationOptions(imageGenerationOptionsDto)));
    }

    @PostMapping(value = "/generate-description", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<GeneratedDescriptionResponseDto> generateDescription(
            @Valid @RequestBody DescriptionGenerationOptionsDto descriptionGenerationOptionsDto) {
        return ResponseEntity.ok(postGenerationUseCase.generateDescription(
                descriptionGenerationOptionsMapper.toDescriptionGenerationOptions(descriptionGenerationOptionsDto)));
    }
}
