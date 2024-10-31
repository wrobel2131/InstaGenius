package com.instagenius.postmanagementservice.infrastructure.rest;

import com.instagenius.postmanagementservice.infrastructure.config.FeignConfig;
import com.instagenius.postmanagementservice.infrastructure.dto.CreateDescriptionDto;
import com.instagenius.postmanagementservice.infrastructure.dto.CreateImageDto;
import com.instagenius.postmanagementservice.infrastructure.dto.GeneratedDescriptionDto;
import com.instagenius.postmanagementservice.infrastructure.dto.GeneratedImageDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@FeignClient(name = "post-generation-service", configuration = FeignConfig.class)
public interface PostGenerationClient {
    @PostMapping("/api/v1/generate/generate-image")
    Optional<GeneratedImageDto> generateImage(CreateImageDto createImageDto);

    @PostMapping("/api/v1/generate/generate-description")
    Optional<GeneratedDescriptionDto> generateDescription(CreateDescriptionDto createDescriptionDto);
}
