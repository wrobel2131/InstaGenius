package com.instagenius.paymentservice.infrastructure.config;

import com.instagenius.paymentservice.infrastructure.exception.PaymentGatewayException;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.net.RequestOptions;
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
            throw new PaymentGatewayException(e.getUserMessage(), HttpStatus.valueOf(e.getStatusCode()));
        }
    }
}
