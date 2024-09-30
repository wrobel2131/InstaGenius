package com.instagenius.coinmanagementservice.infrastructure.mapper;


import com.instagenius.coinmanagementservice.domain.Balance;
import com.instagenius.coinmanagementservice.domain.UserBalance;
import com.instagenius.coinmanagementservice.infrastructure.dto.UserBalanceResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserBalanceMapper {
    UserBalanceMapper INSTANCE = Mappers.getMapper(UserBalanceMapper.class);


    @Mapping(source = "balance", target = "balance", qualifiedByName = "balanceToInt")
    UserBalanceResponseDto toUserBalanceResponseDto(UserBalance userBalance);

    @Named("balanceToInt")
    default int balanceToInt(Balance balance) {
        return balance.balance();
    }
}
