package com.instagenius.postgenerationservice.infrastructure.rest;


import com.instagenius.postgenerationservice.application.PostGenerationUseCase;
import com.instagenius.postgenerationservice.domain.GeneratedDescription;
import com.instagenius.postgenerationservice.domain.GeneratedImage;
import com.instagenius.postgenerationservice.domain.PostGenerationOptions;
import com.instagenius.postgenerationservice.infrastructure.config.openai.GenerationConfig;
import com.instagenius.postgenerationservice.infrastructure.dto.*;
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
@RequestMapping("/api/v1/generation")
class PostGenerationController {
    private final PostGenerationUseCase postGenerationUseCase;
    private static final ImageGenerationOptionsMapper imageGenerationOptionsMapper = ImageGenerationOptionsMapper.INSTANCE;
    private static final DescriptionGenerationOptionsMapper descriptionGenerationOptionsMapper = DescriptionGenerationOptionsMapper.INSTANCE;
    private static final GeneratedDescriptionMapper generatedDescriptionMapper = GeneratedDescriptionMapper.INSTANCE;
    private static final GeneratedImageMapper generatedImageMapper = GeneratedImageMapper.INSTANCE;
    private final GenerationConfig generationConfig;

    @PostMapping(value = "/generate-image", consumes = MediaType.APPLICATION_JSON_VALUE, produces =
            MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<GeneratedImageResponseDto> generateImage(@Valid @RequestBody ImageGenerationOptionsDto imageGenerationOptionsDto) {
//        return ResponseEntity.ok(new GeneratedImageResponseDto(Base64.getEncoder().encodeToString(new byte[3])));
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
//        return ResponseEntity.ok(new GeneratedDescriptionResponseDto("description mock"));
        return ResponseEntity.ok(
                generatedDescriptionMapper.toGeneratedDescriptionResponseDto(
                        postGenerationUseCase.generateDescription(
                                descriptionGenerationOptionsMapper.toDescriptionGenerationOptions(descriptionGenerationOptionsDto)
                        )
                )
        );
    }

    @PostMapping(value = "/calculate-generation-cost", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<GenerationCostResponseDto> calculateGenerationCost(@Valid @RequestBody PostGenerationOptionsDto postGenerationOptionsDto) {
        System.out.println(generationConfig.toString());
        return ResponseEntity.ok(new GenerationCostResponseDto(
                postGenerationUseCase.calculateGenerationCost(new PostGenerationOptions(
                        descriptionGenerationOptionsMapper.toDescriptionGenerationOptions(postGenerationOptionsDto.descriptionGenerationOptions()),
                        imageGenerationOptionsMapper.toImageGenerationOptions(postGenerationOptionsDto.imageGenerationOptions())
                        )
                )
        ));
    }
}
