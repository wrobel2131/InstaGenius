package com.instagenius.postgenerationservice.infrastructure.rest;


import com.instagenius.postgenerationservice.application.PostGenerationUseCase;
import com.instagenius.postgenerationservice.domain.GeneratedDescription;
import com.instagenius.postgenerationservice.domain.GeneratedImage;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post-generation")
@Slf4j
class PostGenerationController {
    private final PostGenerationUseCase postGenerationUseCase;
    private static final ImageGenerationOptionsMapper imageGenerationOptionsMapper = ImageGenerationOptionsMapper.INSTANCE;
    private static final DescriptionGenerationOptionsMapper descriptionGenerationOptionsMapper = DescriptionGenerationOptionsMapper.INSTANCE;
    private static final GeneratedDescriptionMapper generatedDescriptionMapper = GeneratedDescriptionMapper.INSTANCE;
    private static final GeneratedImageMapper generatedImageMapper = GeneratedImageMapper.INSTANCE;

    @PostMapping(value = "/generate-image", consumes = MediaType.APPLICATION_JSON_VALUE, produces =
            MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<GeneratedImageResponseDto> generateImage(@Valid @RequestBody ImageGenerationOptionsDto imageGenerationOptionsDto) {
//        return ResponseEntity.ok(new GeneratedImageResponseDto(Base64.getEncoder().encodeToString(new byte[3])));
        long startTime = System.nanoTime();

        GeneratedImage generatedImage = postGenerationUseCase.generateImage(
                imageGenerationOptionsMapper.toImageGenerationOptions(imageGenerationOptionsDto)
        );

        long endTime = System.nanoTime();

        long elapsedTime = endTime - startTime;
        log.info("elapsed time of generateImage method: {}", elapsedTime);
        return ResponseEntity.ok(
                generatedImageMapper.toGeneratedImageResponseDto(
                        generatedImage
                )
        );
    }

    @PostMapping(value = "/generate-description", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<GeneratedDescriptionResponseDto> generateDescription(@Valid @RequestBody DescriptionGenerationOptionsDto descriptionGenerationOptionsDto) {
//        return ResponseEntity.ok(new GeneratedDescriptionResponseDto("description mock"));
        long startTime = System.nanoTime();
        GeneratedDescription generatedDescription = postGenerationUseCase.generateDescription(
                descriptionGenerationOptionsMapper.toDescriptionGenerationOptions(descriptionGenerationOptionsDto)
        );
        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;
        log.info("elapsed time of generateDescription method: {}", elapsedTime);

        return ResponseEntity.ok(
                generatedDescriptionMapper.toGeneratedDescriptionResponseDto(
                        generatedDescription
                )
        );
    }
}
