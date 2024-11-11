package com.instagenius.postmanagementservice.infrastructure.mapper;

import com.instagenius.postmanagementservice.domain.CoinReservation;
import com.instagenius.postmanagementservice.domain.ReserveCoins;
import com.instagenius.postmanagementservice.infrastructure.dto.CoinReservationResponseDto;
import com.instagenius.postmanagementservice.infrastructure.dto.ReserveCoinsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CoinReservationMapper {
    CoinReservationMapper INSTANCE = Mappers.getMapper(CoinReservationMapper.class);

    @Mapping(source = "amount", target = "amount")
    @Mapping(source = "operationId", target = "operationId")
    ReserveCoinsDto toReserveCoinsDto(ReserveCoins reserveCoins);

    @Mapping(source = "reservationId", target = "reservationId")
    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "amount", target = "amount")
    CoinReservation toCoinReservation(CoinReservationResponseDto coinReservationResponseDto);
}
