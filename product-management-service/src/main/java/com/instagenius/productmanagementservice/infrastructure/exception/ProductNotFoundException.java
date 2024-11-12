package com.instagenius.productmanagementservice.infrastructure.exception;

public class ProductNotFoundException extends RuntimeException {
        public ProductNotFoundException(String message) {
                super(message);
        }
}