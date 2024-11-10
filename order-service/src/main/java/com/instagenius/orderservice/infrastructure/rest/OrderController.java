package com.instagenius.orderservice.infrastructure.rest;

import com.instagenius.orderservice.application.OrderUseCase;
import com.instagenius.orderservice.infrastructure.dto.CreateOrderRequestDto;
import com.instagenius.orderservice.infrastructure.dto.CreatedOrderResponseDto;
import com.instagenius.orderservice.infrastructure.mapper.CreatedOrderMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
class OrderController {
    private final OrderUseCase orderUseCase;
    private final CreatedOrderMapper createdOrderMapper = CreatedOrderMapper.INSTANCE;

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CreatedOrderResponseDto> createOrder(@Valid @RequestBody CreateOrderRequestDto createOrderRequestDto, @AuthenticationPrincipal Jwt jwt) {
        UUID userId = getUserUUIDFromJwtToken(jwt);

        return ResponseEntity.ok(
                createdOrderMapper.toCreatedOrderResponseDto(
                        orderUseCase.createOrder(createOrderRequestDto
                                                         .items()
                                                         .stream()
                                                         .map(createdOrderMapper::toOrderedProduct)
                                                         .toList(), userId)
                )
        );
    }

//    @GetMapping(value = "/{orderId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    ResponseEntity<OrderStatusResponseDto> getOrderStatus(@PathVariable("orderId") UUID orderId, @AuthenticationPrincipal Jwt jwt) {
//
//        return ResponseEntity.ok().build();
//    }


    private UUID getUserUUIDFromJwtToken(Jwt jwt) {
        return UUID.fromString(jwt.getClaim("sub"));
    }
}
