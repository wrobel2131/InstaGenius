package com.instagenius.orderservice.infrastructure.mapper;

import com.instagenius.orderservice.domain.AddCoins;
import com.instagenius.orderservice.infrastructure.dto.AddCoinsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CoinRelatedMapper {
    CoinRelatedMapper INSTANCE = Mappers.getMapper(CoinRelatedMapper.class);

    @Mapping(source = "coins", target = "coins")
    @Mapping(source = "type", target = "type")
    AddCoinsDto toAddCoinsDto(AddCoins addCoins);
}
