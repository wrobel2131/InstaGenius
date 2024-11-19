package com.instagenius.paymentservice.infrastructure.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class PaymentGatewayException extends RuntimeException {
    private final HttpStatus httpStatus;

    public PaymentGatewayException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
