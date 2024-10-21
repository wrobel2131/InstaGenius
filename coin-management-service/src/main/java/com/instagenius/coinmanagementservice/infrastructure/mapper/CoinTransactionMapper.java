package com.instagenius.coinmanagementservice.infrastructure.mapper;


import com.instagenius.coinmanagementservice.domain.CoinAmount;
import com.instagenius.coinmanagementservice.domain.CoinTransaction;
import com.instagenius.coinmanagementservice.domain.TransactionType;
import com.instagenius.coinmanagementservice.infrastructure.adapters.CoinTransactionEntity;
import com.instagenius.coinmanagementservice.infrastructure.dto.CoinTransactionResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CoinTransactionMapper {
    CoinTransactionMapper INSTANCE = Mappers.getMapper(CoinTransactionMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "amount", target = "amount", qualifiedByName = "intToCoinAmount")
    @Mapping(source = "type", target = "type")
    @Mapping(source = "createdAt", target = "createdAt")
    CoinTransaction toCoinTransaction(CoinTransactionEntity coinTransactionEntity);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "amount", target = "amount", qualifiedByName = "coinAmountToInt")
    @Mapping(source = "type", target = "type")
    @Mapping(source = "createdAt", target = "createdAt")
    CoinTransactionEntity toCoinTransactionEntity(CoinTransaction coinTransaction);

    @Mapping(source = "amount", target = "amount", qualifiedByName = "coinAmountToInt")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "type", target = "type", qualifiedByName = "transactionTypeToString")
    CoinTransactionResponseDto toCoinTransactionResponseDto(CoinTransaction coinTransaction);

    @Named("transactionTypeToString")
    default String transactionTypeToString(TransactionType type) {
        return type.name();
    }

    @Named("coinAmountToInt")
    default int mapCoinAmountToIny(CoinAmount amount) {
        return amount.amount();
    }

    @Named("intToCoinAmount")
    default CoinAmount mapIntToCoinAmount(int amount) {
        return new CoinAmount(amount);
    }


}
