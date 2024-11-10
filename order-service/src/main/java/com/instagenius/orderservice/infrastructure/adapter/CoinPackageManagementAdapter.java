package com.instagenius.orderservice.infrastructure.adapter;

import com.instagenius.orderservice.application.CoinPackageManagementPort;
import com.instagenius.orderservice.domain.CoinPackage;
import com.instagenius.orderservice.infrastructure.exception.CoinPackageManagementException;
import com.instagenius.orderservice.infrastructure.exception.FeignExceptionUtils;
import com.instagenius.orderservice.infrastructure.mapper.CoinPackageMapper;
import com.instagenius.orderservice.infrastructure.rest.CoinPackageManagementClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CoinPackageManagementAdapter implements CoinPackageManagementPort {
    private final CoinPackageManagementClient coinPackageManagementClient;
    private static final CoinPackageMapper coinPackageMapper = CoinPackageMapper.INSTANCE;

    @Override
    public CoinPackage getCoinPackage(UUID coinPackageId) {
        try {
            return coinPackageMapper.toCoinPackage(
                    coinPackageManagementClient.getCoinPackageById(coinPackageId));
        } catch (FeignException e) {
            String errorMessage = FeignExceptionUtils.parseErrorResponse(e).message();
            throw new CoinPackageManagementException(errorMessage, HttpStatus.valueOf(e.status()));
        }

    }
}
