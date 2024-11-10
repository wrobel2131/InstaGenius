package com.instagenius.orderservice.infrastructure.adapter;

import com.instagenius.orderservice.application.CoinPackageManagementPort;
import com.instagenius.orderservice.application.ProductServiceClient;
import com.instagenius.orderservice.domain.CoinPackage;
import com.instagenius.orderservice.domain.Price;
import com.instagenius.orderservice.domain.Product;
import com.instagenius.orderservice.domain.ProductType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CoinPackageServiceClient implements ProductServiceClient {
    private final CoinPackageManagementPort coinPackageManagementPort;

    @Override
    public Product getProductById(UUID productId) {
        System.out.println("Getting product by id: " + productId);
        CoinPackage coinPackage = coinPackageManagementPort.getCoinPackage(productId);
        return new Product(coinPackage.id(), coinPackage.name(), coinPackage.description(),
                           getProductType(), new Price(coinPackage.price(), coinPackage.currency()));
    }

    @Override
    public ProductType getProductType() {
        System.out.println("Getting product type in CoinPackageServiceClient");
        return ProductType.COIN_PACKAGE;
    }
}
