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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class StripeResourceAdapter implements PaymentGatewayResourcePort {
    private final StripeProperties stripeProperties;

    @Override
    public PaymentGatewayProduct createPaymentGatewayProduct(Product product) {
        ProductCreateParams productCreateParams = ProductCreateParams.builder()
                                                                     .setName(product.getName())
                                                                     .setDescription(product.getDescription())
                                                                     .setType(ProductCreateParams.Type.SERVICE)
                                                                     .setActive(product.isActive())
                                                                     .putAllMetadata(createMetadata(product))
                                                                     .addImage(product.getImageUrl())
                                                                     .setShippable(false)
                                                                     .build();

        RequestOptions requestOptions = StripeApiUtils.createRequestOptions(stripeProperties.getApiKey());
        com.stripe.model.Product stripeProduct = StripeApiUtils.createProduct(productCreateParams, requestOptions);

        log.debug(String.valueOf(stripeProduct));

        PriceCreateParams priceCreateParams = PriceCreateParams
                .builder()
                .setProduct(stripeProduct.getId())
                .setCurrency(product.getPrice().getCurrency())
                .setUnitAmountDecimal(product.getPrice().getAmount().multiply(BigDecimal.valueOf(100)))
                .putAllMetadata(createMetadata(product))
                .build();

        Price stripePrice = StripeApiUtils.createPrice(priceCreateParams, requestOptions);

        log.debug(String.valueOf(stripePrice));

        return new PaymentGatewayProduct(stripeProduct.getId(), stripePrice.getId());
    }

    @Override
    public PaymentGatewayProduct updatePaymentGatewayProduct(Product product) {
        RequestOptions requestOptions = StripeApiUtils.createRequestOptions(stripeProperties.getApiKey());
        String stripeProductId = product.getPaymentGatewayProductParams().get("paymentGatewayProductId").toString();
        String oldStripePriceId = product.getPaymentGatewayProductParams().get("paymentGatewayProductPriceId")
                                         .toString();

        /* Update the product */
        ProductUpdateParams productUpdateParams = ProductUpdateParams.builder()
                                                                     .setName(product.getName())
                                                                     .setDescription(product.getDescription())
                                                                     .setActive(product.isActive())
                                                                     .putAllMetadata(createMetadata(product))
                                                                     .addImage(product.getImageUrl())
                                                                     .setShippable(false)
                                                                     .build();


        com.stripe.model.Product stripeProduct = StripeApiUtils.updateProduct(stripeProductId, productUpdateParams,
                                                                              requestOptions);

        /* Deactivate old price */
        PriceUpdateParams updateParams = PriceUpdateParams.builder()
                                                          .setActive(false)
                                                          .build();
        Price oldPrice = StripeApiUtils.updatePrice(oldStripePriceId, updateParams, requestOptions);

        /* Create new price for product */
        PriceCreateParams priceCreateParams = PriceCreateParams
                .builder()
                .setProduct(stripeProduct.getId())
                .setCurrency(product.getPrice().getCurrency() != null ? product.getPrice().getCurrency() :
                                     oldPrice.getCurrency())
                .setUnitAmountDecimal(product.getPrice().getAmount() != null ?
                                              product.getPrice().getAmount().multiply(BigDecimal.valueOf(100)) :
                                              oldPrice.getUnitAmountDecimal())
                .putAllMetadata(oldPrice.getMetadata())
                .build();

        Price stripePrice = StripeApiUtils.createPrice(priceCreateParams, requestOptions);

        log.debug(String.valueOf(stripePrice));


        return new PaymentGatewayProduct(stripeProduct.getId(), stripePrice.getId());
    }

    /* Needs to be deleted before persistence delete in database */
    @Override
    public void archivePaymentGatewayProduct(Product product) {
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
            ProductUpdateParams productUpdateParams = ProductUpdateParams.builder()
                                                                         .setActive(false)
                                                                         .build();
            StripeApiUtils.updateProduct(stripeProductId, productUpdateParams, requestOptions);
        }
    }

    private Map<String, String> createMetadata(Product product) {
        Map<String, String> metadata = product.getAttributes() == null ? new HashMap<>() :
                product
                        .getAttributes()
                        .entrySet()
                        .stream()
                        .filter(e -> e.getValue() != null)
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                e -> e.getValue().toString()));
        metadata.put("productId", product.getId().toString());
        metadata.put("productType", product.getType().name());
        return metadata;
    }
}
