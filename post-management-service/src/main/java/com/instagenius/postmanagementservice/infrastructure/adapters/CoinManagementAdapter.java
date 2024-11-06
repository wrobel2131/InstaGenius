package com.instagenius.postmanagementservice.infrastructure.adapters;

import com.instagenius.postmanagementservice.application.CoinManagementPort;
import com.instagenius.postmanagementservice.domain.CoinReservation;
import com.instagenius.postmanagementservice.domain.ReserveCoins;
import com.instagenius.postmanagementservice.infrastructure.exception.CoinManagementException;
import com.instagenius.postmanagementservice.infrastructure.mapper.CoinReservationMapper;
import com.instagenius.postmanagementservice.infrastructure.rest.CoinManagementClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
class CoinManagementAdapter implements CoinManagementPort {
    private final CoinManagementClient coinManagementClient;
    private static final CoinReservationMapper coinReservationMapper = CoinReservationMapper.INSTANCE;

    @Override
    public CoinReservation reserveCoins(ReserveCoins reserveCoins) {
        return coinReservationMapper.toCoinReservation(
                coinManagementClient.reserveCoins(
                        coinReservationMapper.toReserveCoinsDto(reserveCoins)
                ).orElseThrow(() -> new CoinManagementException("Error while reserving coins!"))
        );
    }

    @Override
    public void completeReservation(UUID reservationId) {
        coinManagementClient.completeReservation(reservationId);
    }

    @Override
    public void cancelReservation(UUID reservationId) {
        coinManagementClient.cancelReservation(reservationId);
    }
}
