package com.instagenius.productmanagementservice.infrastructure.adapter;

import com.instagenius.productmanagementservice.application.PaymentGatewayResourcePort;
import com.instagenius.productmanagementservice.domain.PaymentGatewayProduct;
import com.instagenius.productmanagementservice.domain.Product;
import com.instagenius.productmanagementservice.infrastructure.config.StripeApiUtils;
import com.instagenius.productmanagementservice.infrastructure.config.StripeProperties;
import com.stripe.model.Price;
import com.stripe.net.RequestOptions;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.PriceUpdateParams;
import com.stripe.param.ProductCreateParams;
import com.stripe.param.ProductUpdateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class StripeResourceAdapter implements PaymentGatewayResourcePort {
    private final StripeProperties stripeProperties;

    @Override
    public PaymentGatewayProduct createPaymentGatewayProduct(Product product) {
        ProductCreateParams productCreateParams = ProductCreateParams.builder()
                .setName(product.getName())
                .setDescription(product.getDescription())
                .setType(ProductCreateParams.Type.SERVICE)
                .setActive(product.isActive())
                .putAllExtraParam(createExtraParams(product))
                .setShippable(false)
                .build();

        RequestOptions requestOptions = StripeApiUtils.createRequestOptions(stripeProperties.getApiKey());
        com.stripe.model.Product stripeProduct = StripeApiUtils.createProduct(productCreateParams, requestOptions);

        System.out.println(stripeProduct);

        PriceCreateParams priceCreateParams = PriceCreateParams
                .builder()
                .setProduct(stripeProduct.getId())
                .setCurrency(product.getPrice().currency())
                .setUnitAmountDecimal(product.getPrice().price())
                .putAllExtraParam(createExtraParams(product))
                .build();

        Price stripePrice = StripeApiUtils.createPrice(priceCreateParams, requestOptions);

        System.out.println(stripePrice);

        return new PaymentGatewayProduct(stripeProduct.getId(), stripePrice.getId());
    }

    @Override
    public PaymentGatewayProduct updatePaymentGatewayProduct(Product product) {
        RequestOptions requestOptions = StripeApiUtils.createRequestOptions(stripeProperties.getApiKey());
        String stripeProductId = product.getPaymentGatewayProductParams().get("paymentGatewayProductId").toString();
        String oldStripePriceId = product.getPaymentGatewayProductParams().get("paymentGatewayProductPriceId").toString();

        /* Update the product */
        ProductUpdateParams productUpdateParams = ProductUpdateParams.builder()
                .setName(product.getName())
                .setDescription(product.getDescription())
                .setActive(product.isActive())
                .putAllExtraParam(createExtraParams(product))
                .setShippable(false)
                .build();

        com.stripe.model.Product stripeProduct = StripeApiUtils.updateProduct(stripeProductId, productUpdateParams, requestOptions);

        /* Create new price for product */
        PriceCreateParams priceCreateParams = PriceCreateParams
                .builder()
                .setProduct(stripeProduct.getId())
                .setCurrency(product.getPrice().currency())
                .setUnitAmountDecimal(product.getPrice().price())
                .putAllExtraParam(createExtraParams(product))
                .build();

        Price stripePrice = StripeApiUtils.createPrice(priceCreateParams, requestOptions);

        System.out.println(stripePrice);

        /* Deactivate old price */
        PriceUpdateParams updateParams = PriceUpdateParams.builder()
                .setActive(false)
                .build();
        StripeApiUtils.updatePrice(oldStripePriceId, updateParams, requestOptions);

        return new PaymentGatewayProduct(stripeProduct.getId(), stripePrice.getId());
    }

    /* Needs to be deleted before persistence delete in database */
    @Override
    public void deletePaymentGatewayProduct(Product product) {
        RequestOptions requestOptions = StripeApiUtils.createRequestOptions(stripeProperties.getApiKey());

        String stripeProductId = product.getPaymentGatewayProductParams().get("paymentGatewayProductId").toString();
        String stripePriceId = product.getPaymentGatewayProductParams().get("paymentGatewayProductPriceId").toString();


        if (stripePriceId != null) {
            /* Deactivate old price for this product */
            PriceUpdateParams updateParams = PriceUpdateParams.builder()
                    .setActive(false)
                    .build();
            StripeApiUtils.updatePrice(stripePriceId, updateParams, requestOptions);
        }

        if (stripeProductId != null) {
            StripeApiUtils.deleteProduct(stripeProductId, requestOptions);
        }
    }

    private Map<String, Object> createExtraParams(Product product) {
        return Map.of(
                "productId", product.getId().toString(),
                "type", product.getType().name()
        );
    }
}
