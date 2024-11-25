package com.instagenius.userservice.infrastructure.exception;

public class FailedUserUpdateException extends RuntimeException{
    public FailedUserUpdateException(String message) {
        super(message);
    }
}
