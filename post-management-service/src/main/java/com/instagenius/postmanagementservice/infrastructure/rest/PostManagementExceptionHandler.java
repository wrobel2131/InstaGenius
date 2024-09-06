package com.instagenius.postmanagementservice.infrastructure.rest;

import com.instagenius.postmanagementservice.infrastructure.exception.ImageStorageException;
import com.instagenius.postmanagementservice.infrastructure.exception.PostGenerationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
class PostManagementExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<ErrorResponse.ErrorDetail> errors = ex
                .getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> new ErrorResponse.ErrorDetail(((FieldError) error).getField(), error.getDefaultMessage()))
                .toList();
        return new ResponseEntity<>(new ErrorResponse("DTO validation failed!", LocalDateTime.now(), errors),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage(), LocalDateTime.now(), List.of()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PostGenerationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ErrorResponse> handlePostGenerationException(PostGenerationException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage(), LocalDateTime.now(), List.of()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ImageStorageException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ResponseEntity<ErrorResponse> handleImageStorageException(ImageStorageException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage(), LocalDateTime.now(), List.of()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Handler for overall exception thrown by this service, i
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        System.out.println(ex.getMessage());
        return new ResponseEntity<>(new ErrorResponse("Server Internal Error!", LocalDateTime.now(), List.of()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}