package com.instagenius.coinmanagementservice.application;

import com.instagenius.coinmanagementservice.domain.CoinReservation;
import com.instagenius.coinmanagementservice.domain.ReservationStatus;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface CoinReservationPersistencePort {
    CoinReservation save(CoinReservation coinReservation);
    CoinReservation findCoinReservationByIdAndUserId(UUID reservationId, UUID userId);
    CoinReservation findCoinReservationByOperationId(UUID operationId);
    List<CoinReservation> findAllCoinReservationsByStatusAndExpiryTimeBefore(ReservationStatus status, Instant expiryTime);
}
