package com.instagenius.paymentservice.infrastructure.adapter;

import com.instagenius.paymentservice.application.AsyncPaymentProcessPort;
import com.instagenius.paymentservice.application.OrderPort;
import com.instagenius.paymentservice.domain.CompleteOrder;
import com.instagenius.paymentservice.domain.Payment;
import com.instagenius.paymentservice.domain.PaymentData;
import com.instagenius.paymentservice.domain.PaymentStatus;
import com.instagenius.paymentservice.infrastructure.config.StripeApiUtils;
import com.instagenius.paymentservice.infrastructure.config.StripeProperties;
import com.stripe.model.Charge;
import com.stripe.net.RequestOptions;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AsyncPaymentProcessAdapter implements AsyncPaymentProcessPort {
    private final PaymentRepository paymentRepository;
    private final StripeProperties stripeProperties;
    private final OrderPort orderPort;

    @Async
    @Transactional
    @Override
    public void processPaymentData(PaymentData paymentData) {
        if (paymentData == null) return;
        System.out.println("Processing payment data: " + paymentData);
        RequestOptions requestOptions = StripeApiUtils.createRequestOptions(stripeProperties.getApiKey());
        Charge charge = StripeApiUtils.getCharge(paymentData.latestCharge(), requestOptions);

        String paymentId = paymentData.paymentGatewayPaymentMetadata().get("paymentId");
        System.out.println("Payment ID: " + paymentId);
        Payment payment = paymentRepository.getPaymentById(UUID.fromString(paymentId));

        Map<String, String> metadata = payment.getPaymentGatewayMetadata();
        metadata.put("paymentGatewayPaymentId", paymentData.paymentGatewayPaymentId());
        metadata.put("paymentGatewayPaymentMethodId", paymentData.paymentGatewayPaymentMethodId());
        metadata.put("paymentMethodReceiptUrl", charge.getReceiptUrl());

        payment.setStatus(PaymentStatus.COMPLETED);

        paymentRepository.save(payment);


        /* Call to order service to complete order and add coins, performed here because of async nature of this
        method */
        orderPort.completeOrder(payment.getOrderId(), new CompleteOrder(payment.getId()));
    }

}

