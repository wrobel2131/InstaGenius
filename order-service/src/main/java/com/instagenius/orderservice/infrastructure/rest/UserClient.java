package com.instagenius.orderservice.infrastructure.rest;

import com.instagenius.orderservice.infrastructure.config.FeignConfig;
import com.instagenius.orderservice.infrastructure.dto.UserResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "user-service", configuration = FeignConfig.class)
public interface UserClient {

    @GetMapping(value = "/api/v1/users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    UserResponseDto getUserProfile(@PathVariable("userId") UUID userId);
}
