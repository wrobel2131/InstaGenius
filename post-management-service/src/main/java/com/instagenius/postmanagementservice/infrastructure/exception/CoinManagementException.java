package com.instagenius.postmanagementservice.infrastructure.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CoinManagementException extends RuntimeException {
    private final HttpStatus httpStatus;
    public CoinManagementException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
