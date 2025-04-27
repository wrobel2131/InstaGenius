package com.instagenius.productmanagementservice.infrastructure.config;

import com.instagenius.productmanagementservice.infrastructure.exception.PaymentGatewayException;
import com.stripe.exception.StripeException;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.net.RequestOptions;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.PriceUpdateParams;
import com.stripe.param.ProductCreateParams;
import com.stripe.param.ProductUpdateParams;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;


@UtilityClass
@Slf4j
public class StripeApiUtils {

    public RequestOptions createRequestOptions(String apiKey) {
        return RequestOptions.builder()
                .setApiKey(apiKey)
                .build();
    }

    public Product createProduct(ProductCreateParams productCreateParams, RequestOptions requestOptions) {
        log.debug("Creating stripe product");
        try {
            return Product.create(productCreateParams, requestOptions);
        } catch (StripeException e) {
            throw new PaymentGatewayException(e.getUserMessage(), HttpStatus.valueOf(e.getStatusCode()));
        }
    }

    public Product updateProduct(String productId, ProductUpdateParams productUpdateParams, RequestOptions requestOptions) {
        log.debug("Updating stripe product: {}", productUpdateParams);
        try {
            Product product = Product.retrieve(productId, requestOptions);
            return product.update(productUpdateParams, requestOptions);
        } catch (StripeException e) {
            throw new PaymentGatewayException(e.getUserMessage(), HttpStatus.valueOf(e.getStatusCode()));
        }
    }

    public Price createPrice(PriceCreateParams priceCreateParams, RequestOptions requestOptions) {
        log.debug("Creating stripe price");
        try {
            return Price.create(priceCreateParams, requestOptions);
        } catch (StripeException e) {
            throw new PaymentGatewayException(e.getUserMessage(), HttpStatus.valueOf(e.getStatusCode()));
        }
    }

    public Price updatePrice(String priceId, PriceUpdateParams priceUpdateParams, RequestOptions requestOptions) {
        log.debug("Updating stripe price: {}", priceUpdateParams);
        try {
            Price price = Price.retrieve(priceId, requestOptions);
            return price.update(priceUpdateParams, requestOptions);
        } catch (StripeException e) {
            throw new PaymentGatewayException(e.getUserMessage(), HttpStatus.valueOf(e.getStatusCode()));
        }
    }
}
