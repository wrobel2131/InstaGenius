package com.instagenius.productmanagementservice.domain;

import com.instagenius.productmanagementservice.application.FileStoragePort;
import com.instagenius.productmanagementservice.application.PaymentGatewayResourcePort;
import com.instagenius.productmanagementservice.application.ProductPersistencePort;
import com.instagenius.productmanagementservice.application.ProductManagementUseCase;
import com.instagenius.productmanagementservice.infrastructure.exception.InvalidProductImageUrlException;
import jakarta.transaction.Transactional;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ProductManagementService implements ProductManagementUseCase {
    private final ProductPersistencePort productPersistencePort;
    private final PaymentGatewayResourcePort paymentGatewayResourcePort;
    private final FileStoragePort fileStoragePort; //TODO what is used for?

    public ProductManagementService(ProductPersistencePort productPersistencePort, PaymentGatewayResourcePort paymentGatewayResourcePort, FileStoragePort fileStoragePort) {
        this.productPersistencePort = productPersistencePort;
        this.paymentGatewayResourcePort = paymentGatewayResourcePort;
        this.fileStoragePort = fileStoragePort;
    }

    @Override
    public Product createProduct(String name, String description, ProductType type, Price price, String imageUrl,Map<String, Object> attributes) {
        if(!isValidImageUrl(imageUrl)) {
            throw new InvalidProductImageUrlException("Invalid image url!");
        }
        Product product = new Product(name, description, type, price, Instant.now(), Instant.now(), true, 0,
         imageUrl, attributes, null);
        PaymentGatewayProduct paymentGatewayProduct = paymentGatewayResourcePort.createPaymentGatewayProduct(product);
        setPaymentGatewayParamsInProduct(paymentGatewayProduct, product);
        return productPersistencePort.saveProduct(product);
    }

    @Transactional
    @Override
    public Product updateProduct(
            UUID id, String name, String description, ProductType type, Price price, String imageUrl, Map<String,
            Object> attributes,
            Boolean isActive) {
        if(imageUrl != null && !isValidImageUrl(imageUrl)) {
            throw new InvalidProductImageUrlException("Invalid image url!");
        }

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
        if (price.getAmount() != null) {
            product.getPrice().setAmount(price.getAmount());
        }

        if (price.getCurrency() != null) {
            product.getPrice().setCurrency(price.getCurrency());
        }

        if (imageUrl != null) {
            product.setImageUrl(imageUrl);
        }

        if (attributes != null) {
            product.setAttributes(attributes);
        }
        if (isActive != null) {
            product.setActive(isActive);
        }

        PaymentGatewayProduct paymentGatewayProduct = paymentGatewayResourcePort.updatePaymentGatewayProduct(product);
        setPaymentGatewayParamsInProduct(paymentGatewayProduct, product);
        return productPersistencePort.saveProduct(product);
    }

    @Transactional
    @Override
    public Product archiveProduct(UUID id) {
        Product product = productPersistencePort.getProductById(id, null);
        product.setActive(false);
        paymentGatewayResourcePort.archivePaymentGatewayProduct(product);
        return productPersistencePort.saveProduct(product);
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

    private void setPaymentGatewayParamsInProduct(PaymentGatewayProduct paymentGatewayProduct, Product product) {
        Map<String, Object> paymentGatewayProductParams = Map.of(
                "paymentGatewayProductId", paymentGatewayProduct.id(),
                "paymentGatewayProductPriceId", paymentGatewayProduct.priceId()
        );
        product.setPaymentGatewayProductParams(paymentGatewayProductParams);
    }

    private boolean isValidImageUrl(String url) {
        try {
            URL imageUrl = URI.create(url).toURL();
            HttpURLConnection connection = (HttpURLConnection) imageUrl.openConnection();
            connection.setRequestMethod("HEAD");
            connection.connect();
            String contentType = connection.getContentType();
            return contentType != null && contentType.startsWith("image/");
        } catch (IOException e) {
            return false;
        }
    }
}
