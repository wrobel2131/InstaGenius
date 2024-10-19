package com.instagenius.coinmanagementservice.infrastructure.mapper;


import com.instagenius.coinmanagementservice.domain.Balance;
import com.instagenius.coinmanagementservice.domain.UserBalance;
import com.instagenius.coinmanagementservice.infrastructure.adapters.UserBalanceEntity;
import com.instagenius.coinmanagementservice.infrastructure.dto.UserBalanceResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserBalanceMapper {
    UserBalanceMapper INSTANCE = Mappers.getMapper(UserBalanceMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "balance", target = "balance", qualifiedByName = "intToBalance")
    @Mapping(source = "updatedAt", target = "updatedAt")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "version", target = "version")
    UserBalance toUserBalance(UserBalanceEntity userBalanceEntity);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "balance", target = "balance", qualifiedByName = "balanceToInt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "version", target = "version")
    UserBalanceEntity toUserBalanceEntity(UserBalance userBalance);

    @Mapping(source = "balance", target = "balance", qualifiedByName = "balanceToInt")
    UserBalanceResponseDto toUserBalanceResponseDto(UserBalance userBalance);

    @Named("intToBalance")
    default Balance mapToBalance(int balance) {
        return new Balance(balance);
    }

    @Named("balanceToInt")
    default int mapBalanceToInt(Balance balance) {
        return balance.balance();
    }
}
