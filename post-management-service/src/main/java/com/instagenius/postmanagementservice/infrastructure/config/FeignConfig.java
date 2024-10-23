package com.instagenius.postmanagementservice.infrastructure.config;

import com.instagenius.postmanagementservice.infrastructure.rest.FeignClientInterceptor;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;

public class FeignConfig {

    @Bean
    public RequestInterceptor feignClientInterceptor() {
        return new FeignClientInterceptor();
    }
}
