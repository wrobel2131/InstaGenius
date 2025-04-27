package com.instagenius.orderservice.infrastructure.rest;

import com.instagenius.orderservice.application.OrderUseCase;
import com.instagenius.orderservice.infrastructure.dto.*;
import com.instagenius.orderservice.infrastructure.mapper.OrderRelatedMapper;
import com.instagenius.orderservice.infrastructure.mapper.OrderMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Slf4j
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
        log.debug("completeOrder");

        orderUseCase.completeOrder(id, orderRelatedMapper.toCompleteOrder(completeOrderRequestDto));

        log.debug("completedOrder");
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/{id}/cancel", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> cancelOrder(@PathVariable("id") UUID id, @RequestBody CancelOrderRequestDto cancelOrderRequestDto,
                                       @AuthenticationPrincipal Jwt jwt) {
        log.debug("cancelOrder");

        orderUseCase.cancelOrder(id, orderRelatedMapper.toCancelOrder(cancelOrderRequestDto));

        log.debug("canceledOrder");
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/{id}/fail", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> failOrder(@PathVariable("id") UUID id, @RequestBody FailOrderRequestDto failOrderRequestDto,
                                     @AuthenticationPrincipal Jwt jwt) {
        log.debug("failOrder");

        orderUseCase.failOrder(id, orderRelatedMapper.toFailOrder(failOrderRequestDto));

        log.debug("failedOrder");
        return ResponseEntity.noContent().build();
    }


    private UUID getUserUUIDFromJwtToken(Jwt jwt) {
        return UUID.fromString(jwt.getClaim("sub"));
    }
}
