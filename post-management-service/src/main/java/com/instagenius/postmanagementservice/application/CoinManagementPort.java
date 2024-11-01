package com.instagenius.postmanagementservice.application;

import com.instagenius.postmanagementservice.domain.CancelReservation;
import com.instagenius.postmanagementservice.domain.CoinReservation;
import com.instagenius.postmanagementservice.domain.CompleteReservation;
import com.instagenius.postmanagementservice.domain.ReserveCoins;
import com.instagenius.postmanagementservice.infrastructure.dto.CancelReservationDto;

public interface CoinManagementPort {
    CoinReservation reserveCoins(ReserveCoins reserveCoins);
    void completeReservation(CompleteReservation completeReservation);
    void cancelReservation(CancelReservation cancelReservation);
}
