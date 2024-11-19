package com.instagenius.paymentservice.infrastructure.adapter;

import com.instagenius.paymentservice.application.EventPersistencePort;
import com.instagenius.paymentservice.application.PaymentGatewayPort;
import com.instagenius.paymentservice.domain.*;
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
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StripeAdapter implements PaymentGatewayPort {
    private final StripeProperties stripeProperties;
    private final EventPersistencePort eventPersistencePort;

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
    public PaymentData getPaymentDataFromEvent(String payload, String header, PaymentStatus paymentStatus) {
        System.out.println("getting payment data from event");
        String webhookSigningKey = getValidWebhookSigningSecretKey(paymentStatus);
        com.stripe.model.Event stripeEvent = StripeApiUtils.constructEvent(payload, header, webhookSigningKey);
        String eventType = stripeEvent.getType();
        System.out.println("event type: " + eventType);
        System.out.println("payment status: " + paymentStatus);
        if(eventPersistencePort.existsByEventId(stripeEvent.getId())) {
            System.out.println("Event already exists");
            return null;
        }
        if ((paymentStatus.equals(PaymentStatus.COMPLETED) && eventType.equals("payment_intent.succeeded")) ||
                (paymentStatus.equals(PaymentStatus.CANCELLED) && eventType.equals("payment_intent.canceled")) ||
                (paymentStatus.equals(PaymentStatus.FAILED) && eventType.equals("payment_intent.payment_failed"))) {
            System.out.println("Valid type of event");
            eventPersistencePort.saveEvent(new Event(null, stripeEvent.getId(), eventType, stripeEvent.getLivemode(),
                                                     stripeEvent.getCreated(), 0));
            Optional<StripeObject> stripeObject =
                    stripeEvent.getDataObjectDeserializer().getObject();
            if (stripeObject.isPresent()) {
                PaymentIntent paymentIntent = (PaymentIntent) stripeObject.get();
                return new PaymentData(paymentIntent.getId(), eventType, paymentIntent.getMetadata(),
                                       paymentIntent.getPaymentMethod(), paymentIntent.getLatestCharge(),
                                       paymentIntent.getCancellationReason());
            }
        }
        System.out.println("No valid type of event");
        return null;
    }

    private String getValidWebhookSigningSecretKey(PaymentStatus paymentStatus) {
        switch (paymentStatus) {
            case COMPLETED -> {
                return stripeProperties.getSuccessfulPaymentKey();
            }
            case CANCELLED -> {
                return stripeProperties.getCancelledPaymentKey();
            }
            case FAILED -> {
                return stripeProperties.getFailedPaymentKey();
            }
            default -> throw new PaymentGatewayException("Unauthorized!", HttpStatus.UNAUTHORIZED);
        }
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
}
