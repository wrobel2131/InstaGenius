package com.instagenius.coinmanagementservice.infrastructure.mapper;


import com.instagenius.coinmanagementservice.domain.CoinAmount;
import com.instagenius.coinmanagementservice.domain.CoinTransaction;
import com.instagenius.coinmanagementservice.domain.TransactionType;
import com.instagenius.coinmanagementservice.infrastructure.dto.CoinTransactionResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CoinTransactionMapper {
    CoinTransactionMapper INSTANCE = Mappers.getMapper(CoinTransactionMapper.class);

    @Mapping(source = "amount", target = "amount", qualifiedByName = "coinAmountToInt")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "type", target = "type", qualifiedByName = "transactionTypeToString")
    CoinTransactionResponseDto toCoinTransactionResponseDto(CoinTransaction coinTransaction);

    @Named("transactionTypeToString")
    default String transactionTypeToString(TransactionType transactionType) {
        return transactionType.name();
    }

    @Named("coinAmountToInt")
    default int coinAmountToInt(CoinAmount coinAmount) {
        return coinAmount.amount();
    }


}
