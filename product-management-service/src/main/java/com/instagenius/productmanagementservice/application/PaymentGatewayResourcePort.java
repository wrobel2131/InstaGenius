package com.instagenius.productmanagementservice.application;

import com.instagenius.productmanagementservice.domain.PaymentGatewayProduct;
import com.instagenius.productmanagementservice.domain.Product;

import java.util.UUID;

public interface PaymentGatewayResourcePort {
    PaymentGatewayProduct createPaymentGatewayProduct(Product product);
    PaymentGatewayProduct updatePaymentGatewayProduct(Product product);
    void archivePaymentGatewayProduct(Product product);
}
