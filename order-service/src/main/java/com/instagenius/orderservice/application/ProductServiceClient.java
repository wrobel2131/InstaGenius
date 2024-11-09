package com.instagenius.orderservice.application;

import com.instagenius.orderservice.domain.Product;
import com.instagenius.orderservice.domain.ProductType;

import java.util.UUID;
/*Interface for services, which will get product info from external source, for example: Getting coin package info*/
public interface ProductServiceClient {
    Product getProductById(UUID productId);
    ProductType getProductType();
}
