package com.instagenius.postmanagementservice.infrastructure.config;


import com.instagenius.postmanagementservice.application.*;
import com.instagenius.postmanagementservice.domain.PostManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
class PostManagementConfig {
    private final PostPersistencePort postPersistencePort;
    private final PostGenerationPort postGenerationPort;
    private final CoinManagementPort coinManagementPort;
    private final FileStoragePort fileStoragePort;

    @Bean
    PostManagementUseCase postManagementUseCase() {
        return new PostManagementService(postPersistencePort, postGenerationPort, coinManagementPort, fileStoragePort);
    }
}
