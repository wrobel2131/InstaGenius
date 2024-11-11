package com.instagenius.orderservice.infrastructure.mapper;

import com.instagenius.orderservice.domain.AddCoins;
import com.instagenius.orderservice.domain.CoinPackage;
import com.instagenius.orderservice.infrastructure.dto.AddCoinsDto;
import com.instagenius.orderservice.infrastructure.dto.CoinPackageResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CoinRelatedMapper {
    CoinRelatedMapper INSTANCE = Mappers.getMapper(CoinRelatedMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "coins", target = "coins")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "currency", target = "currency")
    CoinPackage toCoinPackage(CoinPackageResponseDto responseDto);

    @Mapping(source = "coins", target = "coins")
    @Mapping(source = "type", target = "type")
    AddCoinsDto toAddCoinsDto(AddCoins addCoins);
}
