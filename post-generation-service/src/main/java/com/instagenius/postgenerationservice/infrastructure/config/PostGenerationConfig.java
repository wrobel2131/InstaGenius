package com.instagenius.postgenerationservice.infrastructure.config;

import com.instagenius.postgenerationservice.application.PostGenerationOutputPort;
import com.instagenius.postgenerationservice.application.PostGenerationUseCase;
import com.instagenius.postgenerationservice.domain.PostGenerationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
class PostGenerationConfig {
    private final PostGenerationOutputPort postGenerationOutputPort;

    @Bean
    public PostGenerationUseCase postGenerationUseCase() {
        return new PostGenerationService(postGenerationOutputPort);
    }
}
