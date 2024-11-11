package com.instagenius.postgenerationservice.infrastructure.exception;

public class GenerationException extends RuntimeException {
    public GenerationException(String message) {
        super(message);
    }
}
