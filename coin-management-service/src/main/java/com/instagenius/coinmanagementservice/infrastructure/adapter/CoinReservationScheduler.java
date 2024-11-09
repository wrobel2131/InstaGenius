package com.instagenius.coinmanagementservice.infrastructure.adapter;

import com.instagenius.coinmanagementservice.application.CoinManagementUseCase;
import com.instagenius.coinmanagementservice.application.CoinReservationPersistencePort;
import com.instagenius.coinmanagementservice.domain.CoinReservation;
import com.instagenius.coinmanagementservice.domain.ReservationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
class CoinReservationScheduler {
    private final CoinReservationPersistencePort coinReservationPersistencePort;
    private final CoinManagementUseCase coinManagementUseCase;

    @Scheduled(fixedRate = 300000) /* Runs every 5 min */
    public void cancelExpiredReservations() {
        System.out.println("Scheduled task, cancelling expired reservations");
        List<CoinReservation> coinReservations = coinReservationPersistencePort.findAllCoinReservationsByStatusAndExpiryTimeBefore(ReservationStatus.PENDING, Instant.now());
        for (CoinReservation coinReservation: coinReservations) {
            coinManagementUseCase.cancelReservation(coinReservation.getUserId(), coinReservation.getId());
        }

    }

}
