package com.instagenius.paymentservice.infrastructure.adapter;

import com.instagenius.paymentservice.application.PaymentGatewayPort;
import com.instagenius.paymentservice.domain.Payment;
import com.instagenius.paymentservice.domain.Price;
import com.instagenius.paymentservice.domain.Product;
import com.instagenius.paymentservice.infrastructure.config.StripeApiUtils;
import com.instagenius.paymentservice.infrastructure.config.StripeProperties;
import com.instagenius.paymentservice.infrastructure.exception.PaymentGatewayException;
import com.stripe.model.checkout.Session;
import com.stripe.net.RequestOptions;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class StripeAdapter implements PaymentGatewayPort {
    private final StripeProperties stripeProperties;
    @Override
    public Payment createPaymentSession(Payment payment, Map<Product, Integer> products) {
        RequestOptions requestOptions = StripeApiUtils.createRequestOptions(stripeProperties.getApiKey());

        List<SessionCreateParams.LineItem> lineItems = products
                .entrySet()
                .stream()
                .map(e -> {
                    Product product = e.getKey();
                    String stripeProductId = product.paymentGatewayProductParams().get("paymentGatewayProductId").toString();
                    String stripePriceId = product.paymentGatewayProductParams().get("paymentGatewayProductPriceId").toString();
                    if (stripePriceId == null || stripeProductId == null) {
                        throw new PaymentGatewayException("Error while creating payment checkout session!",
                                HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                    return SessionCreateParams.LineItem
                            .builder()
                            .setPrice(stripePriceId)
                            .setQuantity(e.getValue().longValue())
                            .build();
                }).toList();

        SessionCreateParams sessionCreateParams = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("some success url")
                .setCancelUrl("some cancel url")
                .addAllLineItem(lineItems)
                .setExpiresAt(Instant.now().plus(15, ChronoUnit.MINUTES).getEpochSecond())
                .setAllowPromotionCodes(false)
                .setCurrency(payment.getPrice().currency())
                .putMetadata("paymentId", payment.getId().toString())
                .build();

        Session session = StripeApiUtils.createSession(sessionCreateParams, requestOptions);
        String sessionCheckoutId = session.getId();
        String paymentIntentId = session.getPaymentIntent();
        Long amountTotalPrice = session.getAmountTotal();
        String currency = session.getCurrency().toUpperCase();

        payment.setPrice(new Price(BigDecimal.valueOf(amountTotalPrice).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP), currency));
        payment.setPaymentGatewayCheckoutSessionId(sessionCheckoutId);
        payment.setPaymentGatewayPaymentId(paymentIntentId);

        return payment;
    }
}
