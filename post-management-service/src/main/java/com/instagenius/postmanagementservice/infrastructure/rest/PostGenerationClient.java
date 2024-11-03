package com.instagenius.postmanagementservice.infrastructure.rest;

import com.instagenius.postmanagementservice.infrastructure.config.FeignConfig;
import com.instagenius.postmanagementservice.infrastructure.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@FeignClient(name = "post-generation-service",configuration = FeignConfig.class)
public interface PostGenerationClient {
    @PostMapping("/api/v1/generation/generate-image")
    Optional<GeneratedImageDto> generateImage(@RequestBody CreateImageDto createImageDto);

    @PostMapping("/api/v1/generation/generate-description")
    Optional<GeneratedDescriptionDto> generateDescription(@RequestBody CreateDescriptionDto createDescriptionDto);

    @PostMapping("/api/v1/generation/calculate-generation-cost")
    Optional<GenerationCostDto> calculateGenerationCost(@RequestBody CreatePostOptionsDto createPostOptionsDto);
}
