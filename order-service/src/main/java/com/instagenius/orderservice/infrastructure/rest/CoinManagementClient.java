package com.instagenius.orderservice.infrastructure.rest;

import com.instagenius.orderservice.infrastructure.config.FeignConfig;
import com.instagenius.orderservice.infrastructure.dto.AddCoinsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "coin-management-service", configuration = FeignConfig.class)
public interface CoinManagementClient {

    @PostMapping(value = "/api/v1/coins/add", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    void addCoins(@RequestBody AddCoinsDto addCoinsDto);
}