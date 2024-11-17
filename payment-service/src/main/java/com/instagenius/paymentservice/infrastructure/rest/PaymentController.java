package com.instagenius.paymentservice.infrastructure.rest;

import com.instagenius.paymentservice.application.PaymentUseCase;
import com.instagenius.paymentservice.infrastructure.config.StripeApiUtils;
import com.instagenius.paymentservice.infrastructure.dto.InitializePaymentRequestDto;
import com.instagenius.paymentservice.infrastructure.dto.InitializedPaymentResponseDto;
import com.instagenius.paymentservice.infrastructure.mapper.PaymentMapper;
import com.instagenius.paymentservice.infrastructure.mapper.ProductRelatedMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping ("/api/v1/payments")
public class PaymentController {
    private final PaymentUseCase paymentUseCase;
    private static final PaymentMapper paymentMapper = PaymentMapper.INSTANCE;
    private static final ProductRelatedMapper productRelatedMapper = ProductRelatedMapper.INSTANCE;

    @PostMapping(value = "/initialize", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<InitializedPaymentResponseDto> initializePayment(@RequestBody @Valid InitializePaymentRequestDto initializePaymentRequestDto, @AuthenticationPrincipal Jwt jwt) {
        UUID userId = getUserUUIDFromJwtToken(jwt);

        return ResponseEntity.ok(paymentMapper.toInitializedPaymentResponseDto(
                paymentUseCase.initializePayment(userId, initializePaymentRequestDto.orderId(),
                        initializePaymentRequestDto.referenceId(),
                        initializePaymentRequestDto
                                .orderedProducts()
                                .stream()
                                .map(productRelatedMapper::toOrderedProduct)
                                .toList())
        ));
    }

    @PostMapping(value = "/webhooks/successful-payment")
    ResponseEntity<Void> handleSuccessfulPayment(@RequestBody String eventPayload,
                                                 @RequestHeader("Stripe-Signature") String signatureHeader) {
        System.out.println("eventPayload: " + eventPayload);
        paymentUseCase.handlePaymentSuccess(eventPayload, signatureHeader);

        System.out.println("Payment successful");
        return ResponseEntity.ok().build();

    }

    private UUID getUserUUIDFromJwtToken(Jwt jwt) {
        return UUID.fromString(jwt.getClaim("sub"));
    }
}
