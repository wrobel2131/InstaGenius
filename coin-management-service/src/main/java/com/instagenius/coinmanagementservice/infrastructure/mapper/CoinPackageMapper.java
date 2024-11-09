package com.instagenius.coinmanagementservice.infrastructure.mapper;

import com.instagenius.coinmanagementservice.domain.CoinAmount;
import com.instagenius.coinmanagementservice.domain.CoinPackage;
import com.instagenius.coinmanagementservice.infrastructure.adapter.CoinPackageEntity;
import com.instagenius.coinmanagementservice.infrastructure.dto.CoinPackageResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CoinPackageMapper {
    CoinPackageMapper INSTANCE = Mappers.getMapper(CoinPackageMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target ="name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "coinAmount", target = "coinAmount", qualifiedByName = "coinAmountToInt")
    @Mapping(source = "price.price", target = "price")
    @Mapping(source = "price.currency", target = "currency")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt", ignore = true)
    @Mapping(source = "active", target = "active")
    @Mapping(source = "version", target = "version")
    CoinPackageEntity toCoinPackageEntity(CoinPackage coinPackage);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target ="name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "coinAmount", target = "coinAmount", qualifiedByName = "intToCoinAmount")
    @Mapping(target = "price", expression = "java(new Price(coinPackageEntity.getPrice(), coinPackageEntity.getCurrency()))")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    @Mapping(source = "active", target = "active")
    @Mapping(source = "version", target = "version")
    CoinPackage toCoinPackage(CoinPackageEntity coinPackageEntity);

    @Named("coinAmountToInt")
    default int coinAmountToInt(CoinAmount coinAmount) {
        return coinAmount.amount();
    }

    @Named("intToCoinAmount")
    default CoinAmount intToCoinAmount(int coinAmount) {
        return new CoinAmount(coinAmount);
    }

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "price.price", target = "price")
    @Mapping(source = "coinAmount", target = "coins", qualifiedByName = "coinAmountToInt")
    @Mapping(source = "price.currency", target = "currency")
    @Mapping(source = "type", target = "type")
    CoinPackageResponseDto toCoinPackageResponseDto(CoinPackage coinPackage);
}
