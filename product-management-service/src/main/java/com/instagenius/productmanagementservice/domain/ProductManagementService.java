package com.instagenius.productmanagementservice.domain;

import com.instagenius.productmanagementservice.application.PaymentGatewayResourcePort;
import com.instagenius.productmanagementservice.application.ProductPersistencePort;
import com.instagenius.productmanagementservice.application.ProductManagementUseCase;
import jakarta.transaction.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ProductManagementService implements ProductManagementUseCase {
    private final ProductPersistencePort productPersistencePort;
    private final PaymentGatewayResourcePort paymentGatewayResourcePort;

    public ProductManagementService(ProductPersistencePort productPersistencePort, PaymentGatewayResourcePort paymentGatewayResourcePort) {
        this.productPersistencePort = productPersistencePort;
        this.paymentGatewayResourcePort = paymentGatewayResourcePort;
    }

    @Override
    public Product createProduct(String name, String description, ProductType type, Price price, Map<String, Object> attributes) {
        Product product = new Product(name, description, type, price, Instant.now(), Instant.now(), true, 0, attributes, null);
        PaymentGatewayProduct paymentGatewayProduct = paymentGatewayResourcePort.createPaymentGatewayProduct(product);
        setPaymentGatewayParams(paymentGatewayProduct, product);
        return productPersistencePort.saveProduct(product);
    }

    @Transactional
    @Override
    public Product updateProduct(
            UUID id, String name, String description, ProductType type, Price price, Map<String, Object> attributes,
            Boolean isActive) {
        Product product = productPersistencePort.getProductById(id, null);

        if (name != null) {
            product.setName(name);
        }
        if (description != null) {
            product.setDescription(description);
        }
        if (type != null) {
            product.setType(type);
        }
        if (price != null) {
            product.setPrice(price);
        }
        if (attributes != null) {
            product.setAttributes(attributes);
        }
        if (isActive != null) {
            product.setActive(isActive);
        }

        PaymentGatewayProduct paymentGatewayProduct = paymentGatewayResourcePort.updatePaymentGatewayProduct(product);
        setPaymentGatewayParams(paymentGatewayProduct, product);
        return productPersistencePort.saveProduct(product);
    }

    @Transactional
    @Override
    public void deleteProduct(UUID id) {
        Product product = productPersistencePort.getProductById(id, null);
        paymentGatewayResourcePort.deletePaymentGatewayProduct(product);
        productPersistencePort.deleteProduct(id);
    }

    @Override
    public Product getProductById(UUID id) {
        return productPersistencePort.getProductById(id, null);
    }

    @Override
    public List<Product> getActiveProducts(ProductType type) {
        return productPersistencePort.getProducts(type, true);
    }

    @Override
    public List<Product> getProductsByIds(List<UUID> ids) {
        return productPersistencePort.getProductsByIds(ids);
    }

    private void setPaymentGatewayParams(PaymentGatewayProduct paymentGatewayProduct, Product product) {
        Map<String, Object> paymentGatewayProductParams = Map.of(
                "paymentGatewayProductId", paymentGatewayProduct.id(),
                "paymentGatewayProductPriceId", paymentGatewayProduct.priceId()
        );
        product.setPaymentGatewayProductParams(paymentGatewayProductParams);
    }
}
