package com.instagenius.userservice.infrastructure.rest;

import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import com.instagenius.userservice.infrastructure.exception.FailedUserUpdateException;
import com.instagenius.userservice.infrastructure.exception.UserNotFoundException;
import jakarta.ws.rs.NotAuthorizedException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.Instant;
import java.util.List;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
class UserExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage(), Instant.now(), List.of()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    ResponseEntity<ErrorResponse> handleNotAuthorizedException(NotAuthorizedException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage(), Instant.now(), List.of()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ErrorResponse> handleFailedUserUpdateException(FailedUserUpdateException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage(), Instant.now(), List.of()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    ResponseEntity<ErrorResponse> handleAuthorizationDeniedException(AuthorizationDeniedException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage(), Instant.now(), List.of()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage(), Instant.now(), List.of()), HttpStatus.BAD_REQUEST);
    }

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

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        return new ResponseEntity<>(new ErrorResponse("Invalid parameter in path!", Instant.now(), List.of()),
                                    HttpStatus.BAD_REQUEST);
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

        ErrorResponse errorResponse = new ErrorResponse(
                "Malformed JSON request",
                Instant.now(),
                List.of()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(new ErrorResponse("Server Internal Error!", Instant.now(), List.of()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
