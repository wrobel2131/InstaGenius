package com.instagenius.coinmanagementservice.infrastructure.exception;

public class CoinPackageNotFoundException extends RuntimeException {
    public CoinPackageNotFoundException(String message) {
        super(message);
    }
}
