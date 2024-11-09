package com.instagenius.orderservice.infrastructure.adapter;

import com.instagenius.orderservice.application.CoinPackageManagementPort;
import com.instagenius.orderservice.domain.CoinPackage;
import com.instagenius.orderservice.infrastructure.exception.CoinPackageManagementException;
import com.instagenius.orderservice.infrastructure.mapper.CoinPackageMapper;
import com.instagenius.orderservice.infrastructure.rest.CoinPackageManagementClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CoinPackageManagementAdapter implements CoinPackageManagementPort {
    private final CoinPackageManagementClient coinPackageManagementClient;
    private static final CoinPackageMapper coinPackageMapper = CoinPackageMapper.INSTANCE;

    @Override
    public CoinPackage getCoinPackage(UUID coinPackageId) {
        return coinPackageMapper.toCoinPackage(
                coinPackageManagementClient.getCoinPackageById(coinPackageId).orElseThrow(() -> new CoinPackageManagementException("Error while getting coin package!"))
        );
    }
}
