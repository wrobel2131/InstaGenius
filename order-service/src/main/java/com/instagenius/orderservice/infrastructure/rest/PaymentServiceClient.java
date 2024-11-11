package com.instagenius.orderservice.infrastructure.rest;

import com.instagenius.orderservice.infrastructure.config.FeignConfig;
import com.instagenius.orderservice.infrastructure.dto.CreatedPaymentResponseDto;
import com.instagenius.orderservice.infrastructure.dto.InitializePaymentRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-service", configuration = FeignConfig.class)
public interface PaymentServiceClient {

    @GetMapping(value = "/api/v1/payments", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    CreatedPaymentResponseDto initializePayment(@RequestBody InitializePaymentRequestDto initializePaymentRequestDto);
}
