package com.instagenius.postgenerationservice.infrastructure.exception;

public class InvalidGenerationOptionsException extends RuntimeException{
    public InvalidGenerationOptionsException(String message) {
        super(message);
    }
}
