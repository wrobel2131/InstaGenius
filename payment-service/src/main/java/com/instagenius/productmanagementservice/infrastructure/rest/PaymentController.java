package com.instagenius.productmanagementservice.infrastructure.rest;

import com.instagenius.productmanagementservice.application.PaymentUseCase;
import com.instagenius.productmanagementservice.infrastructure.dto.CreatedPaymentDto;
import com.instagenius.productmanagementservice.infrastructure.dto.InitializePaymentRequestDto;
import com.instagenius.productmanagementservice.infrastructure.mapper.PaymentMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping ("/api/v1/payments")
public class PaymentController {
    private final PaymentUseCase paymentUseCase;
    private final PaymentMapper paymentMapper = PaymentMapper.INSTANCE;

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CreatedPaymentDto> initializePayment(@RequestBody @Valid InitializePaymentRequestDto initializePaymentRequestDto, @AuthenticationPrincipal Jwt jwt) {
        UUID userId = getUserUUIDFromJwtToken(jwt);
        return ResponseEntity.ok(
                paymentMapper.toCreatedPaymentDto(
                        paymentUseCase.initializePayment(userId, initializePaymentRequestDto.orderId(),
                                                         initializePaymentRequestDto.price(),
                                                         initializePaymentRequestDto.currency())
                )
        );
    }

    private UUID getUserUUIDFromJwtToken(Jwt jwt) {
        return UUID.fromString(jwt.getClaim("sub"));
    }
}
