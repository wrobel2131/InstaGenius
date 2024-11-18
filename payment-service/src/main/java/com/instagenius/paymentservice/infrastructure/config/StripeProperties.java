package com.instagenius.paymentservice.infrastructure.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "stripe")
@Getter
@Setter
public class StripeProperties {
    private String apiKey;
    private String successfulPaymentKey;
    private String failedPaymentKey;
    private String cancelledPaymentKey;
}
