package com.instagenius.coinmanagementservice.infrastructure.adapters;


import com.instagenius.coinmanagementservice.application.CoinReservationPersistencePort;
import com.instagenius.coinmanagementservice.domain.CoinReservation;
import com.instagenius.coinmanagementservice.domain.ReservationStatus;
import com.instagenius.coinmanagementservice.infrastructure.exception.CoinReservationNotFoundException;
import com.instagenius.coinmanagementservice.infrastructure.mapper.CoinReservationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class CoinReservationRepository implements CoinReservationPersistencePort {
    private final JpaCoinReservationRepository jpaCoinReservationRepository;
    private static final CoinReservationMapper coinReservationMapper = CoinReservationMapper.INSTANCE;
    @Override
    public CoinReservation save(CoinReservation coinReservation) {
        return coinReservationMapper.toCoinReservation(
                jpaCoinReservationRepository.save(
                        coinReservationMapper.toCoinReservationEntity(coinReservation)
                )
        );
    }

    @Override
    public CoinReservation findCoinReservationByIdAndUserId(UUID reservationId, UUID userId) {
        return coinReservationMapper.toCoinReservation(
                jpaCoinReservationRepository.findCoinReservationEntityByIdAndUserId(reservationId, userId)
                        .orElseThrow(() -> new CoinReservationNotFoundException("Coin reservation with id " + reservationId + " not found!"))
        );
    }

    @Override
    public CoinReservation findCoinReservationByOperationId(UUID operationId) {
        return coinReservationMapper.toCoinReservation(
                jpaCoinReservationRepository.findCoinReservationEntityByOperationId(operationId)
                        .orElseThrow(() -> new CoinReservationNotFoundException("Coin reservation with operation id " + operationId + " not found!"))
        );
    }

    @Override
    public List<CoinReservation> findAllCoinReservationsByStatusAndExpiryTimeBefore(ReservationStatus status, Instant expiryTime) {
        return jpaCoinReservationRepository.findCoinReservationEntitiesByStatusAndExpiryTimeBefore(status, expiryTime)
                .stream()
                .map(coinReservationMapper::toCoinReservation)
                .toList();
    }
}

@Repository
interface JpaCoinReservationRepository  extends JpaRepository<CoinReservationEntity, UUID> {
    Optional<CoinReservationEntity> findCoinReservationEntityByIdAndUserId(UUID reservationId, UUID userId);
    Optional<CoinReservationEntity> findCoinReservationEntityByOperationId(UUID operationId);
    List<CoinReservationEntity> findCoinReservationEntitiesByStatusAndExpiryTimeBefore(ReservationStatus status, Instant expiryTime);
}
