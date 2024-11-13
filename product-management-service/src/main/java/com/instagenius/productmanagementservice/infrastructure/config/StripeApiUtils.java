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
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;


@RequiredArgsConstructor
@UtilityClass
public class StripeApiUtils {

    public RequestOptions createRequestOptions(String apiKey) {
        return RequestOptions.builder()
                .setApiKey(apiKey)
                .build();
    }

    public Product createProduct(ProductCreateParams productCreateParams, RequestOptions requestOptions) {
        try {
            return Product.create(productCreateParams, requestOptions);
        } catch (StripeException e) {
            throw new PaymentGatewayException(e.getUserMessage(), HttpStatus.valueOf(e.getStatusCode()));
        }
    }

    public Product updateProduct(String productId, ProductUpdateParams productUpdateParams, RequestOptions requestOptions) {
        try {
            Product product = Product.retrieve(productId, requestOptions);
            return product.update(productUpdateParams, requestOptions);
        } catch (StripeException e) {
            throw new PaymentGatewayException(e.getUserMessage(), HttpStatus.valueOf(e.getStatusCode()));
        }
    }

    public void deleteProduct(String productId, RequestOptions requestOptions) {
        try {
            Product product = Product.retrieve(productId, requestOptions);
            product.delete();
        } catch (StripeException e) {
            throw new PaymentGatewayException(e.getUserMessage(), HttpStatus.valueOf(e.getStatusCode()));
        }
    }

    public Price createPrice(PriceCreateParams priceCreateParams, RequestOptions requestOptions) {
        try {
            return Price.create(priceCreateParams, requestOptions);
        } catch (StripeException e) {
            throw new PaymentGatewayException(e.getUserMessage(), HttpStatus.valueOf(e.getStatusCode()));
        }
    }

    public Price updatePrice(String priceId, PriceUpdateParams priceUpdateParams, RequestOptions requestOptions) {
        try {
            Price price = Price.retrieve(priceId, requestOptions);
            return price.update(priceUpdateParams, requestOptions);
        } catch (StripeException e) {
            throw new PaymentGatewayException(e.getUserMessage(), HttpStatus.valueOf(e.getStatusCode()));
        }
    }
}
