package com.instagenius.paymentservice.infrastructure.config;

import com.instagenius.paymentservice.infrastructure.rest.FeignClientInterceptor;
import feign.RequestInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;

@Configuration
@RequiredArgsConstructor
public class FeignConfig {
    private final OAuth2AuthorizedClientManager authorizedClientManager;

    @Bean
    public RequestInterceptor feignClientInterceptor() {
        return new FeignClientInterceptor(authorizedClientManager);
    }
}
