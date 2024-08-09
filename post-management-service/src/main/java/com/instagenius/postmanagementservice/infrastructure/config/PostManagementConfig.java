package com.instagenius.postmanagementservice.infrastructure.config;


import com.instagenius.postmanagementservice.application.PostGenerationPort;
import com.instagenius.postmanagementservice.application.PostManagementUseCase;
import com.instagenius.postmanagementservice.application.PostPersistencePort;
import com.instagenius.postmanagementservice.domain.PostManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class PostManagementConfig {
    private final PostPersistencePort postPersistencePort;
    private final PostGenerationPort postGenerationPort;

    @Bean
    PostManagementUseCase postManagementUseCase() {
        return new PostManagementService(postPersistencePort, postGenerationPort);
    }
}
