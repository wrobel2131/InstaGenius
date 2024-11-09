package com.instagenius.postmanagementservice.infrastructure.rest;

import com.instagenius.postmanagementservice.infrastructure.config.FeignConfig;
import com.instagenius.postmanagementservice.infrastructure.dto.CoinReservationResponseDto;
import com.instagenius.postmanagementservice.infrastructure.dto.ReserveCoinsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;
import java.util.UUID;

@FeignClient(name = "coin-management-service", configuration = FeignConfig.class)
public interface CoinManagementClient {
    @PostMapping(value = "/api/v1/coins/reserve", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    Optional<CoinReservationResponseDto> reserveCoins(@RequestBody ReserveCoinsDto reserveCoinsDto);

    @PostMapping("/api/v1/coins/complete/{reservationId}")
    void completeReservation(@PathVariable("reservationId") UUID reservationId);

    @PostMapping("/api/v1/coins/cancel/{reservationId}")
    void cancelReservation(@PathVariable("reservationId") UUID reservationId);

}
