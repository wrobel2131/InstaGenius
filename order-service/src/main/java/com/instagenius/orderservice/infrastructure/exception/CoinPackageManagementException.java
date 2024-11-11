package com.instagenius.orderservice.infrastructure.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CoinPackageManagementException extends RuntimeException {
    private final HttpStatus httpStatus;
    public CoinPackageManagementException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
