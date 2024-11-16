package com.instagenius.paymentservice.infrastructure.rest;

import com.instagenius.paymentservice.infrastructure.config.FeignConfig;
import com.instagenius.paymentservice.infrastructure.dto.CompleteOrderRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(name = "order-service", configuration = FeignConfig.class)
public interface OrderClient {
    @PostMapping(value = "/api/v1/orders/{id}/complete", consumes = MediaType.APPLICATION_JSON_VALUE)
    void completeOrder(@PathVariable("id") UUID id, @RequestBody CompleteOrderRequestDto completeOrderRequestDto);
}
