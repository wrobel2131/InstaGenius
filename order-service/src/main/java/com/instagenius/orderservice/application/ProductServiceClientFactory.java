package com.instagenius.orderservice.application;

import com.instagenius.orderservice.domain.ProductType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

// Factory class which handles specific client to call external service, based on the product type
@Service
public class ProductServiceClientFactory {

    private final Map<ProductType, ProductServiceClient> clientMap;

    public ProductServiceClientFactory(List<ProductServiceClient> clients) {
        System.out.println("ProductServiceClientFactory constructor");
        clientMap = clients.stream()
                           .collect(Collectors.toMap(ProductServiceClient::getProductType, Function.identity()));
    }

    public ProductServiceClient getClient(ProductType productType) {
        System.out.println("Map: " + clientMap);
        System.out.println("ProductServiceClientFactory getClient of type: " + productType);
        return clientMap.get(productType);
    }
}
