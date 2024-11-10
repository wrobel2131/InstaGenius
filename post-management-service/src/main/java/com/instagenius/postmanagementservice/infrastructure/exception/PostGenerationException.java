package com.instagenius.postmanagementservice.infrastructure.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class PostGenerationException extends RuntimeException {
    private final HttpStatus httpStatus;
    public PostGenerationException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
