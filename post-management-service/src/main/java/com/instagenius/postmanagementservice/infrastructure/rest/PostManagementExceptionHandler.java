package com.instagenius.postmanagementservice.infrastructure.rest;

import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import com.instagenius.postmanagementservice.infrastructure.exception.ImageStorageException;
import com.instagenius.postmanagementservice.infrastructure.exception.PostGenerationException;
import com.instagenius.postmanagementservice.infrastructure.exception.PostNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.Instant;
import java.util.List;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
class PostManagementExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>(new ErrorResponse("Request body not valid!", Instant.now(),
                ex
                        .getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(e -> new ErrorResponse.ErrorDetail(e.getField(), e.getDefaultMessage()))
                        .toList()
        ), HttpStatus.BAD_REQUEST);
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
                        Instant.now(),
                        List.of()
                );
                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
            }
        }

        // Fallback for other HttpMessageNotReadableException cases
        ErrorResponse errorResponse = new ErrorResponse(
                "Malformed JSON request",
                Instant.now(),
                List.of()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PostNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseEntity<ErrorResponse> handlePostNotFoundException(PostNotFoundException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage(), Instant.now(), List.of()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PostGenerationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ErrorResponse> handlePostGenerationException(PostGenerationException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage(), Instant.now(), List.of()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        return new ResponseEntity<>(new ErrorResponse("Invalid Id format!", Instant.now(), List.of()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ImageStorageException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ResponseEntity<ErrorResponse> handleImageStorageException(ImageStorageException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage(), Instant.now(), List.of()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Handler for overall exception thrown by this service, i
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(new ErrorResponse("Server Internal Error!", Instant.now(), List.of()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
