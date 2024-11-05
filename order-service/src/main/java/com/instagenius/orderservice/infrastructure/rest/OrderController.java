package com.instagenius.orderservice.infrastructure.rest;

import com.instagenius.orderservice.infrastructure.dto.CoinPackagesResponseDto;
import com.instagenius.orderservice.infrastructure.dto.CreateOrderResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
class OrderController {

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CreateOrderResponseDto> createOrder(@Valid @RequestBody CreateOrderRequestDto createOrderRequestDto, @AuthenticationPrincipal Jwt jwt) {

        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/coin-packages", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CoinPackagesResponseDto> getCoinPackages(@AuthenticationPrincipal Jwt jwt) {

        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{orderId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<OrderStatusResponseDto> getOrderStatus(@PathVariable("orderId") Long orderId, @AuthenticationPrincipal Jwt jwt) {

        return ResponseEntity.ok().build();
    }


}
