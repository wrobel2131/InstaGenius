package com.instagenius.productmanagementservice.infrastructure.exception;

public class InvalidProductImageUrlException extends RuntimeException{
    public InvalidProductImageUrlException(String message) {
        super(message);
    }
}
