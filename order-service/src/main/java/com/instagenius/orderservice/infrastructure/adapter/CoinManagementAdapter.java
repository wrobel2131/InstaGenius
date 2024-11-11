package com.instagenius.orderservice.infrastructure.adapter;

import com.instagenius.orderservice.application.CoinManagementPort;
import com.instagenius.orderservice.domain.AddCoins;
import com.instagenius.orderservice.infrastructure.exception.CoinManagementException;
import com.instagenius.orderservice.infrastructure.exception.FeignExceptionUtils;
import com.instagenius.orderservice.infrastructure.mapper.CoinRelatedMapper;
import com.instagenius.orderservice.infrastructure.rest.CoinManagementClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CoinManagementAdapter implements CoinManagementPort {
    private final CoinManagementClient coinManagementClient;
    private static final CoinRelatedMapper coinRelatedMapper = CoinRelatedMapper.INSTANCE;

    @Override
    public void addCoins(AddCoins addCoins) {
        try {
            coinManagementClient.addCoins(coinRelatedMapper.toAddCoinsDto(addCoins));
        } catch (FeignException e) {
            String errorMessage = FeignExceptionUtils.parseErrorResponse(e).message();
            throw new CoinManagementException(errorMessage, HttpStatus.valueOf(e.status()));
        }
    }
}
