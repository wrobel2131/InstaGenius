package com.instagenius.postmanagementservice.infrastructure.rest;

import com.instagenius.postmanagementservice.infrastructure.config.FeignConfig;
import com.instagenius.postmanagementservice.infrastructure.dto.CancelReservationDto;
import com.instagenius.postmanagementservice.infrastructure.dto.CoinReservationDto;
import com.instagenius.postmanagementservice.infrastructure.dto.CompleteReservationDto;
import com.instagenius.postmanagementservice.infrastructure.dto.ReserveCoinsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@FeignClient(name = "coin-management-service", configuration = FeignConfig.class)
public interface CoinManagementClient {
    @PostMapping("/api/v1/coins/reserve")
    Optional<CoinReservationDto> reserveCoins(@RequestBody ReserveCoinsDto reserveCoinsDto);

    @PostMapping("/api/v1/coins/complete")
    void completeReservation(@RequestBody CompleteReservationDto completeReservationDto);

    @PostMapping("/api/v1/coins/cancel")
    void cancelReservation(@RequestBody CancelReservationDto cancelReservationDto);

}
