package com.instagenius.userservice.infrastructure.config;


import com.instagenius.userservice.application.UserPersistencePort;
import com.instagenius.userservice.application.UserUseCase;
import com.instagenius.userservice.domain.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
class UserConfig {
    private final UserPersistencePort userPersistencePort;

    @Bean
    public UserUseCase userUseCase() {
        return new UserService(userPersistencePort);
    }

}
