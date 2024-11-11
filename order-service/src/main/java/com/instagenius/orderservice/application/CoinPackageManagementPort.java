package com.instagenius.orderservice.application;

import com.instagenius.orderservice.domain.CoinPackage;

import java.util.UUID;

public interface CoinPackageManagementPort {
    CoinPackage getCoinPackage(UUID coinPackageId);
}
