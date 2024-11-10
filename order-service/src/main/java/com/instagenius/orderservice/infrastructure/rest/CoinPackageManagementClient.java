package com.instagenius.orderservice.infrastructure.rest;

import com.instagenius.orderservice.infrastructure.config.FeignConfig;
import com.instagenius.orderservice.infrastructure.dto.CoinPackageResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.UUID;

@FeignClient(name = "coin-management-service", configuration = FeignConfig.class)
public interface CoinPackageManagementClient {

    @GetMapping(value = "/api/v1/coin-packages/{coinPackageId}", produces = MediaType.APPLICATION_JSON_VALUE)
    CoinPackageResponseDto getCoinPackageById(@PathVariable("coinPackageId") UUID coinPackageId);
}
