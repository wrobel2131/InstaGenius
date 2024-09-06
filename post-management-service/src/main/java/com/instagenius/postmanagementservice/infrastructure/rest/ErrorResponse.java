package com.instagenius.postmanagementservice.infrastructure.rest;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponse(String message, LocalDateTime timestamp, List<ErrorDetail> errors) {

    record ErrorDetail(String field, String message) {}
}
