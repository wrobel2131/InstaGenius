package com.instagenius.orderservice.infrastructure.mapper;

import com.instagenius.orderservice.domain.CoinPackage;
import com.instagenius.orderservice.infrastructure.dto.CoinPackageResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CoinPackageMapper {
    CoinPackageMapper INSTANCE = Mappers.getMapper(CoinPackageMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "coins", target = "coins")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "currency", target = "currency")
    CoinPackage toCoinPackage(CoinPackageResponseDto responseDto);
}
