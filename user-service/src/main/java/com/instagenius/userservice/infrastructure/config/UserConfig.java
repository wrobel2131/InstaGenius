package com.instagenius.userservice.infrastructure.config;


import com.instagenius.userservice.application.KeycloakAccountManagementPort;
import com.instagenius.userservice.application.UserPersistencePort;
import com.instagenius.userservice.application.UserSynchronizationUseCase;
import com.instagenius.userservice.application.UserUseCase;
import com.instagenius.userservice.domain.UserService;
import com.instagenius.userservice.domain.UserSynchronizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
class UserConfig {
    private final UserPersistencePort userPersistencePort;
    private final KeycloakAccountManagementPort keycloakAccountManagementPort;

    @Bean
    public UserUseCase userUseCase() {
        return new UserService(userPersistencePort, keycloakAccountManagementPort);
    }

    @Bean
    public UserSynchronizationUseCase userSynchronizationUseCase() {
        return new UserSynchronizationService(userPersistencePort);
    }
}
