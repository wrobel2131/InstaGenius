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

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CoinManagementAdapter implements CoinManagementPort {
    private final CoinManagementClient coinManagementClient;
    private static final CoinRelatedMapper coinRelatedMapper = CoinRelatedMapper.INSTANCE;

    @Override
    public void addCoins(UUID userId, AddCoins addCoins) {
        try {
            System.out.println("Calling addCoins from order service");
            coinManagementClient.addCoins(userId, coinRelatedMapper.toAddCoinsDto(addCoins));
        } catch (FeignException e) {
            System.out.println("Exception occurred while adding coins from order service");
            e.printStackTrace();
            String errorMessage = FeignExceptionUtils.parseErrorResponse(e).message();
            throw new CoinManagementException(errorMessage, HttpStatus.valueOf(e.status()));
        }
    }
}
