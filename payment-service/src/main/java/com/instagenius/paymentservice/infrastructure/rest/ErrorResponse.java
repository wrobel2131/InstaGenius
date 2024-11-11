package com.instagenius.paymentservice.infrastructure.rest;

import java.time.Instant;
import java.util.List;

public record ErrorResponse(String message, Instant timestamp, List<ErrorDetail> errors) {
    record ErrorDetail(String field, String message) {}
}
