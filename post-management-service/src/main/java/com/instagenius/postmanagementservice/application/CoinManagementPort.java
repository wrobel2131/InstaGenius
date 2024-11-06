package com.instagenius.postmanagementservice.application;

import com.instagenius.postmanagementservice.domain.CoinReservation;
import com.instagenius.postmanagementservice.domain.ReserveCoins;

import java.util.UUID;

public interface CoinManagementPort {
    CoinReservation reserveCoins(ReserveCoins reserveCoins);
    void completeReservation(UUID reservationId);
    void cancelReservation(UUID reservationId);
}
