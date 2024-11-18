package com.instagenius.paymentservice.infrastructure.adapter;

import com.instagenius.paymentservice.application.AsyncPaymentProcessPort;
import com.instagenius.paymentservice.application.OrderPort;
import com.instagenius.paymentservice.domain.*;
import com.instagenius.paymentservice.infrastructure.config.StripeApiUtils;
import com.instagenius.paymentservice.infrastructure.config.StripeProperties;
import com.instagenius.paymentservice.infrastructure.exception.PaymentNotFoundException;
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
        String eventType = paymentData.eventType();
        Charge charge = null;
        if (eventType.equals("payment_intent.succeeded")) {
            charge = StripeApiUtils.getCharge(paymentData.latestCharge(), requestOptions);
        }

        String paymentId = paymentData.paymentGatewayPaymentMetadata().get("paymentId");
        if (paymentId == null) {
            throw new PaymentNotFoundException("Payment not found!");
        }
        System.out.println("Payment ID: " + paymentId);
        Payment payment = paymentRepository.getPaymentById(UUID.fromString(paymentId));


        switch (eventType) {
            case "payment_intent.succeeded":
                handleSuccessfulPayment(paymentData, payment, charge);
                break;
            case "payment_intent.canceled":
                handleCanceledPayment(paymentData, payment);
                break;
            case "payment_intent.payment_failed":
                handleFailedPayment(paymentData, payment);
                break;
        }
    }

    void handleSuccessfulPayment(PaymentData paymentData, Payment payment, Charge charge) {
        Map<String, String> metadata = payment.getPaymentGatewayMetadata();
        metadata.put("paymentGatewayPaymentId", paymentData.paymentGatewayPaymentId());
        metadata.put("paymentGatewayPaymentMethodId", paymentData.paymentGatewayPaymentMethodId());
        metadata.put("paymentMethodReceiptUrl", charge.getReceiptUrl());
        payment.setStatus(PaymentStatus.COMPLETED);
        Payment savedPayment = paymentRepository.save(payment);
        orderPort.completeOrder(savedPayment.getOrderId(), new CompleteOrder(savedPayment.getId()));
    }

    void handleCanceledPayment(PaymentData paymentData, Payment payment) {
        Map<String, String> metadata = payment.getPaymentGatewayMetadata();
        metadata.put("paymentGatewayPaymentId", paymentData.paymentGatewayPaymentId());
        metadata.put("paymentCancellationReason", paymentData.cancellationReason());
        payment.setStatus(PaymentStatus.CANCELLED);
        Payment savedPayment = paymentRepository.save(payment);
        orderPort.cancelOrder(savedPayment.getOrderId(), new CancelOrder(savedPayment.getId()));
    }

    void handleFailedPayment(PaymentData paymentData, Payment payment) {
        Map<String, String> metadata = payment.getPaymentGatewayMetadata();
        metadata.put("paymentGatewayPaymentId", paymentData.paymentGatewayPaymentId());
        payment.setStatus(PaymentStatus.FAILED);
        Payment savedPayment = paymentRepository.save(payment);
        orderPort.failOrder(savedPayment.getOrderId(), new FailOrder(savedPayment.getId()));
    }

}

