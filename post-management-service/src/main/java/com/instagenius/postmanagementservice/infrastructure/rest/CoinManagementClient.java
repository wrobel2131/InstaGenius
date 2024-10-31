package com.instagenius.postmanagementservice.infrastructure.rest;

import com.instagenius.postmanagementservice.infrastructure.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "coin-management-service", configuration = FeignConfig.class)
public interface CoinManagementClient {

    //TODO endpoints for coin management service
}
