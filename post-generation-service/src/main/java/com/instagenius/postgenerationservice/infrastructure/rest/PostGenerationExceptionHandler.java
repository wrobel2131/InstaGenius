package com.instagenius.postgenerationservice.infrastructure.rest;

import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import com.instagenius.postgenerationservice.infrastructure.exception.InvalidGenerationOptionsException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
class PostGenerationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>(new ErrorResponse("Request body not valid!", LocalDateTime.now(),
                ex
                        .getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(e -> new ErrorResponse.ErrorDetail(e.getField(), e.getDefaultMessage()))
                        .toList()
        ), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidGenerationOptionsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleInvalidGenerationOptionsException(InvalidGenerationOptionsException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage(), LocalDateTime.now(), List.of()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        Throwable rootCause = ex.getCause();

        if (rootCause instanceof ValueInstantiationException) {
            Throwable cause = rootCause.getCause();
            if (cause instanceof IllegalArgumentException) {
                String message = cause.getMessage();
                ErrorResponse errorResponse = new ErrorResponse(
                        message,
                        LocalDateTime.now(),
                        List.of()
                );
                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
            }
        }

        // Fallback for other HttpMessageNotReadableException cases
        ErrorResponse errorResponse = new ErrorResponse(
                "Malformed JSON request",
                LocalDateTime.now(),
                List.of()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // Handler for overall exception thrown by this service, i
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(new ErrorResponse("Server Internal Error!", LocalDateTime.now(), List.of()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
