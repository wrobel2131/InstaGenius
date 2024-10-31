package com.instagenius.coinmanagementservice.infrastructure.exception;

public class CoinReservationNotFoundException extends RuntimeException {
    public CoinReservationNotFoundException(String message) {
        super(message);
    }
}
