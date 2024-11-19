package com.instagenius.paymentservice.infrastructure.config;

import com.instagenius.paymentservice.infrastructure.exception.PaymentGatewayException;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.net.RequestOptions;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;


@UtilityClass
public class StripeApiUtils {

    public RequestOptions createRequestOptions(String apiKey) {
        return RequestOptions.builder()
                .setApiKey(apiKey)
                .build();
    }

    public Session createSession(SessionCreateParams sessionCreateParams, RequestOptions requestOptions) {
        try {
            return Session.create(sessionCreateParams, requestOptions);
        } catch (StripeException e) {
            e.printStackTrace();
            throw new PaymentGatewayException(e.getUserMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public PaymentIntent getPaymentIntent(String paymentIntentId, RequestOptions requestOptions) {
        try {
            return PaymentIntent.retrieve(paymentIntentId, requestOptions);
        } catch (StripeException e) {
            e.printStackTrace();
            throw new PaymentGatewayException(e.getUserMessage(), HttpStatus.valueOf(e.getStatusCode()));
        }
    }

    public Charge getCharge(String chargeId, RequestOptions requestOptions) {
        try {
            return Charge.retrieve(chargeId, requestOptions);
        } catch (StripeException e) {
            e.printStackTrace();
            throw new PaymentGatewayException(e.getUserMessage(), HttpStatus.valueOf(e.getStatusCode()));
        }
    }

    public Event constructEvent(String eventPayload, String signatureHeader, String endpointSecretKey) {
        try {
            return Webhook.constructEvent(eventPayload, signatureHeader, endpointSecretKey);
        } catch (StripeException e) {
            e.printStackTrace();
            throw new PaymentGatewayException(e.getUserMessage(), HttpStatus.valueOf(e.getStatusCode()));
        }
    }
}
