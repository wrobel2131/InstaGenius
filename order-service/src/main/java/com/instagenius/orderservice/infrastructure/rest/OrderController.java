package com.instagenius.orderservice.infrastructure.rest;

import com.instagenius.orderservice.application.OrderUseCase;
import com.instagenius.orderservice.infrastructure.dto.CompleteOrderRequestDto;
import com.instagenius.orderservice.infrastructure.dto.CreateOrderRequestDto;
import com.instagenius.orderservice.infrastructure.dto.CreatedOrderResponseDto;
import com.instagenius.orderservice.infrastructure.dto.OrderResponseDto;
import com.instagenius.orderservice.infrastructure.mapper.OrderRelatedMapper;
import com.instagenius.orderservice.infrastructure.mapper.OrderMapper;
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
    private static final OrderRelatedMapper orderRelatedMapper = OrderRelatedMapper.INSTANCE;


    @GetMapping(value = "/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<OrderResponseDto> getOrderByOrderId(@PathVariable("orderId") String orderId,
                                                       @AuthenticationPrincipal Jwt jwt) {
        UUID userId = getUserUUIDFromJwtToken(jwt);
        return ResponseEntity.ok(
                OrderMapper.toOrderResponseDto(orderUseCase.findOrderByUserIdAndReferenceId(userId, orderId))
        );
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CreatedOrderResponseDto> createOrder(@Valid @RequestBody CreateOrderRequestDto createOrderRequestDto, @AuthenticationPrincipal Jwt jwt) {
        UUID userId = getUserUUIDFromJwtToken(jwt);

        return ResponseEntity.ok(
                orderRelatedMapper.toCreatedOrderResponseDto(
                        orderUseCase.createOrder(createOrderRequestDto
                                                         .items()
                                                         .stream()
                                                         .map(orderRelatedMapper::toOrderedProduct)
                                                         .toList(), userId)
                )
        );
    }

    @PostMapping(value = "/{id}/complete", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> completeOrder(@PathVariable("id") UUID id, @RequestBody CompleteOrderRequestDto completeOrderRequestDto,
                                       @AuthenticationPrincipal Jwt jwt) {
        UUID userId = getUserUUIDFromJwtToken(jwt);
        orderUseCase.completeOrder(userId, id, orderRelatedMapper.toCompleteOrder(completeOrderRequestDto));

        return ResponseEntity.noContent().build();
    }


    private UUID getUserUUIDFromJwtToken(Jwt jwt) {
        return UUID.fromString(jwt.getClaim("sub"));
    }
}
