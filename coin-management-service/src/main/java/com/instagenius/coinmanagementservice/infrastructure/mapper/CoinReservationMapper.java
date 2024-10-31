package com.instagenius.coinmanagementservice.infrastructure.mapper;

import com.instagenius.coinmanagementservice.domain.CoinReservation;
import com.instagenius.coinmanagementservice.infrastructure.adapters.CoinReservationEntity;
import com.instagenius.coinmanagementservice.infrastructure.dto.CoinReservationDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CoinReservationMapper {
    CoinReservationMapper INSTANCE = Mappers.getMapper(CoinReservationMapper.class);

    CoinReservationDto toCoinReservationDto(CoinReservation coinReservation);

    CoinReservation toCoinReservation(CoinReservationEntity coinReservationEntity);

    CoinReservationEntity toCoinReservationEntity(CoinReservation coinReservation);

    //TODO map coin reservation entity to domain
    //TODO map coin reservation domain to dto
}
