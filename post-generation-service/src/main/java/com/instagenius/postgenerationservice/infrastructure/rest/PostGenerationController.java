package com.instagenius.postgenerationservice.infrastructure.rest;


import com.instagenius.postgenerationservice.application.PostGenerationUseCase;
import com.instagenius.postgenerationservice.infrastructure.dto.DescriptionGenerationOptionsDto;
import com.instagenius.postgenerationservice.infrastructure.dto.GeneratedDescriptionResponseDto;
import com.instagenius.postgenerationservice.infrastructure.dto.ImageGenerationOptionsDto;
import com.instagenius.postgenerationservice.infrastructure.dto.GeneratedImageResponseDto;
import com.instagenius.postgenerationservice.infrastructure.mapper.DescriptionGenerationOptionsMapper;
import com.instagenius.postgenerationservice.infrastructure.mapper.GeneratedDescriptionMapper;
import com.instagenius.postgenerationservice.infrastructure.mapper.GeneratedImageMapper;
import com.instagenius.postgenerationservice.infrastructure.mapper.ImageGenerationOptionsMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post-generation")
class PostGenerationController {
    private final PostGenerationUseCase postGenerationUseCase;
    private static final ImageGenerationOptionsMapper imageGenerationOptionsMapper = ImageGenerationOptionsMapper.INSTANCE;
    private static final DescriptionGenerationOptionsMapper descriptionGenerationOptionsMapper = DescriptionGenerationOptionsMapper.INSTANCE;
    private static final GeneratedDescriptionMapper generatedDescriptionMapper = GeneratedDescriptionMapper.INSTANCE;
    private static final GeneratedImageMapper generatedImageMapper = GeneratedImageMapper.INSTANCE;

    @PostMapping(value = "/generate-image", consumes = MediaType.APPLICATION_JSON_VALUE, produces =
            MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<GeneratedImageResponseDto> generateImage(@Valid @RequestBody ImageGenerationOptionsDto imageGenerationOptionsDto) {
        return ResponseEntity.ok(
                generatedImageMapper.toGeneratedImageResponseDto(
                        postGenerationUseCase.generateImage(
                                imageGenerationOptionsMapper.toImageGenerationOptions(imageGenerationOptionsDto)
                        )
                )
        );
    }

    @PostMapping(value = "/generate-description", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<GeneratedDescriptionResponseDto> generateDescription(@Valid @RequestBody DescriptionGenerationOptionsDto descriptionGenerationOptionsDto) {
        return ResponseEntity.ok(
                generatedDescriptionMapper.toGeneratedDescriptionResponseDto(
                        postGenerationUseCase.generateDescription(
                                descriptionGenerationOptionsMapper.toDescriptionGenerationOptions(descriptionGenerationOptionsDto)
                        )
                )
        );
    }
}
