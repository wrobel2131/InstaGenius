package com.instagenius.coinmanagementservice.infrastructure.mapper;

import com.instagenius.coinmanagementservice.domain.CoinAmount;
import com.instagenius.coinmanagementservice.domain.CoinReservation;
import com.instagenius.coinmanagementservice.infrastructure.adapter.CoinReservationEntity;
import com.instagenius.coinmanagementservice.infrastructure.dto.CoinReservationResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CoinReservationMapper {
    CoinReservationMapper INSTANCE = Mappers.getMapper(CoinReservationMapper.class);

    @Mapping(source = "id", target = "reservationId")
    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "amount", target = "amount", qualifiedByName = "coinAmountToInt")
    CoinReservationResponseDto toCoinReservationDto(CoinReservation coinReservation);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "amount", target = "amount", qualifiedByName = "intToCoinAmount")
    @Mapping(source = "operationId", target = "operationId")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "updatedAt", target = "updatedAt")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "expiryTime", target = "expiryTime")
    @Mapping(source = "version", target = "version")
    CoinReservation toCoinReservation(CoinReservationEntity coinReservationEntity);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "amount", target = "amount", qualifiedByName = "coinAmountToInt")
    @Mapping(source = "operationId", target = "operationId")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "updatedAt", target = "updatedAt", ignore = true)
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "expiryTime", target = "expiryTime")
    @Mapping(source = "version", target = "version")
    CoinReservationEntity toCoinReservationEntity(CoinReservation coinReservation);


    @Named("coinAmountToInt")
    default int mapCoinAmountToInt(CoinAmount coinAmount) {
        return coinAmount.amount();
    }

    @Named("intToCoinAmount")
    default CoinAmount mapCoinAmountToInt(int coinAmount) {
        return new CoinAmount(coinAmount);
    }
}
