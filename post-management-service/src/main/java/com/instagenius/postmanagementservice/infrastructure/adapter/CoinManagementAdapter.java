package com.instagenius.postmanagementservice.infrastructure.adapter;

import com.instagenius.postmanagementservice.application.CoinManagementPort;
import com.instagenius.postmanagementservice.domain.CoinReservation;
import com.instagenius.postmanagementservice.domain.ReserveCoins;
import com.instagenius.postmanagementservice.infrastructure.exception.CoinManagementException;
import com.instagenius.postmanagementservice.infrastructure.exception.FeignExceptionUtils;
import com.instagenius.postmanagementservice.infrastructure.mapper.CoinReservationMapper;
import com.instagenius.postmanagementservice.infrastructure.rest.CoinManagementClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
class CoinManagementAdapter implements CoinManagementPort {
    private final CoinManagementClient coinManagementClient;
    private static final CoinReservationMapper coinReservationMapper = CoinReservationMapper.INSTANCE;

    @Override
    public CoinReservation reserveCoins(ReserveCoins reserveCoins) {
        try {
            return coinReservationMapper.toCoinReservation(
                    coinManagementClient.reserveCoins(
                            coinReservationMapper.toReserveCoinsDto(reserveCoins)
                    )
            );
        } catch (FeignException e) {
            String errorMessage = FeignExceptionUtils.parseErrorResponse(e).message();
            throw new CoinManagementException(errorMessage, HttpStatus.valueOf(e.status()));
        }
    }

    @Override
    public void completeReservation(UUID reservationId) {
        try {
            coinManagementClient.completeReservation(reservationId);
        } catch (FeignException e) {
            String errorMessage = FeignExceptionUtils.parseErrorResponse(e).message();
            throw new CoinManagementException(errorMessage, HttpStatus.valueOf(e.status()));
        }
    }

    @Override
    public void cancelReservation(UUID reservationId) {
        try {
            coinManagementClient.cancelReservation(reservationId);
        } catch (FeignException e) {
            String errorMessage = FeignExceptionUtils.parseErrorResponse(e).message();
            throw new CoinManagementException(errorMessage, HttpStatus.valueOf(e.status()));
        }
    }
}
