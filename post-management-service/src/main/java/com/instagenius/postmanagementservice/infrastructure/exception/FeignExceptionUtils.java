package com.instagenius.postmanagementservice.infrastructure.exception;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.instagenius.postmanagementservice.infrastructure.rest.ErrorResponse;
import feign.FeignException;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.List;

@UtilityClass
public class FeignExceptionUtils {
    public ErrorResponse parseErrorResponse(FeignException e) {
        String responseBody = null;

        if (e.responseBody().isPresent()) {
            ByteBuffer byteBuffer = e.responseBody().get();
            responseBody = StandardCharsets.UTF_8.decode(byteBuffer).toString();
        }

        if (responseBody != null && !responseBody.isEmpty()) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            try {
                return objectMapper.readValue(responseBody, ErrorResponse.class);

            } catch (IOException ioException) {
                System.out.println("Failed to parse error response: " + ioException.getMessage());
                return new ErrorResponse("Failed to parse error response", Instant.now(), List.of());
            }
        } else {
            System.out.println("Response body is null or empty");
            return new ErrorResponse("No response body", Instant.now(), List.of());
        }
    }
}
