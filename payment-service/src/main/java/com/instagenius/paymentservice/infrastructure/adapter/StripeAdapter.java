package com.instagenius.paymentservice.infrastructure.adapter;

import com.instagenius.paymentservice.application.PaymentGatewayPort;
import com.instagenius.paymentservice.domain.PaymentData;
import com.instagenius.paymentservice.domain.Payment;
import com.instagenius.paymentservice.domain.Price;
import com.instagenius.paymentservice.domain.Product;
import com.instagenius.paymentservice.infrastructure.config.StripeApiUtils;
import com.instagenius.paymentservice.infrastructure.config.StripeProperties;
import com.instagenius.paymentservice.infrastructure.exception.PaymentGatewayException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.StripeObject;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
                    String stripeProductId = product.paymentGatewayProductParams().get("paymentGatewayProductId")
                                                    .toString();
                    String stripePriceId = product.paymentGatewayProductParams().get("paymentGatewayProductPriceId")
                                                  .toString();
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
        SessionCreateParams.PaymentIntentData paymentIntentData = SessionCreateParams.PaymentIntentData
                .builder()
                .putAllMetadata(createPaymentIntentMetaData(payment))
                .build();

        SessionCreateParams sessionCreateParams = SessionCreateParams
                .builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("https://example.com/success")
                .setCancelUrl("https://example.com/cancel")
                .addAllLineItem(lineItems)
                .setExpiresAt(
                        Instant.now().plus(30, ChronoUnit.MINUTES)
                               .getEpochSecond())
                .setAllowPromotionCodes(false)
                .setPaymentIntentData(paymentIntentData)
                .build();

        Session session = StripeApiUtils.createSession(sessionCreateParams, requestOptions);
        Long amountTotalPrice = session.getAmountTotal();
        String currency = session.getCurrency().toUpperCase();
        payment.setPrice(
                new Price(BigDecimal.valueOf(amountTotalPrice).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP),
                          currency));
        payment.setPaymentGatewayMetadata(createPaymentGatewayMetaData(session));

        return payment;
    }

    @Override
    public PaymentData getPaymentDataFromEvent(String payload, String header) {
        com.stripe.model.Event stripeEvent = StripeApiUtils.constructEvent(payload, header, stripeProperties.getSuccessfulPaymentKey());
        if (stripeEvent.getType().equals("payment_intent.succeeded")) {
            Optional<StripeObject> stripeObject =
                     stripeEvent.getDataObjectDeserializer().getObject();
            if (stripeObject.isPresent()) {
                PaymentIntent paymentIntent = (PaymentIntent) stripeObject.get();
                return new PaymentData(paymentIntent.getId(), paymentIntent.getMetadata(),
                                       paymentIntent.getPaymentMethod(), paymentIntent.getLatestCharge());
            }
        }
        return null;

    }


    private Map<String, String> createPaymentIntentMetaData(Payment payment) {
        return Map.of("paymentId", payment.getId().toString());
    }

    private Map<String, String> createPaymentGatewayMetaData(Session session) {
        return Map.of(
                "paymentGatewayCheckoutSessionId", session.getId(),
                "paymentGatewayCheckoutSessionUrl", session.getUrl()
                );
    }

    private Map<String, String> extractPaymentData(com.stripe.model.Event event) {
        Map<String, String> paymentData = new HashMap<>();
        //TODO add data

        return paymentData;
    }
}
