package com.instagenius.orderservice.infrastructure.rest;

import com.instagenius.orderservice.infrastructure.config.FeignConfig;
import com.instagenius.orderservice.infrastructure.dto.AddCoinsDto;
import com.instagenius.orderservice.infrastructure.dto.CoinPackageResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(name = "coin-management-service", configuration = FeignConfig.class)
public interface CoinManagementClient {

    @PostMapping(value = "/api/v1/coins/add", produces = MediaType.APPLICATION_JSON_VALUE)
    void addCoins(@RequestBody AddCoinsDto addCoinsDto);

    @GetMapping(value = "/api/v1/coin-packages/{coinPackageId}", produces = MediaType.APPLICATION_JSON_VALUE)
    CoinPackageResponseDto getCoinPackageById(@PathVariable("coinPackageId") UUID coinPackageId);
}