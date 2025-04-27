package com.instagenius.orderservice.infrastructure.exception;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.instagenius.orderservice.infrastructure.rest.ErrorResponse;
import feign.FeignException;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.List;

@Slf4j
@UtilityClass
public class FeignExceptionUtils {
    public ErrorResponse parseErrorResponse(FeignException e) {
        String responseBody = e.responseBody()
                               .map(byteBuffer -> StandardCharsets.UTF_8.decode(byteBuffer).toString())
                               .orElse(null);

        if (responseBody != null && !responseBody.isEmpty()) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            try {
                return objectMapper.readValue(responseBody, ErrorResponse.class);

            } catch (IOException ioException) {
                log.debug("Failed to parse error response: {}", ioException.getMessage());
                return new ErrorResponse("Failed to parse error response", Instant.now(), List.of());
            }
        } else {
            log.debug("Response body is null or empty");
            return new ErrorResponse("No response body", Instant.now(), List.of());
        }
    }
}
