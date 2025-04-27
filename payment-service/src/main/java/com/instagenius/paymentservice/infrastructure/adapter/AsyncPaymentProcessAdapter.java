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
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AsyncPaymentProcessAdapter implements AsyncPaymentProcessPort {
    private final PaymentRepository paymentRepository;
    private final StripeProperties stripeProperties;
    private final OrderPort orderPort;
    private static final String PAYMENT_ID = "paymentId";
    private static final String PAYMENT_INTENT_SUCCEEDED = "payment_intent.succeeded";
    private static final String PAYMENT_INTENT_FAILED = "payment_intent.payment_failed";
    private static final String PAYMENT_INTENT_CANCELLED = "payment_intent.canceled";
    private static final String PAYMENT_NOT_FOUND_ERROR_MESSAGE = "Payment not found!";
    private static final String GATEWAY_PAYMENT_ID = "paymentGatewayPaymentId";
    private static final String GATEWAY_PAYMENT_METHOD_ID = "paymentGatewayPaymentMethodId";
    private static final String PAYMENT_METHOD_RECEIPT_URL = "paymentMethodReceiptUrl";
    private static final String PAYMENT_CANCELLATION_REASON = "paymentCancellationReason";


    @Async
    @Transactional
    @Override
    public void processPaymentData(PaymentData paymentData) {
        if (paymentData == null) {
            log.debug("Payment data is null");
            return;
        }
        log.debug("Processing payment data: {}", paymentData);
        RequestOptions requestOptions = StripeApiUtils.createRequestOptions(stripeProperties.getApiKey());
        String eventType = paymentData.eventType();
        Charge charge = null;
        if (eventType.equals(PAYMENT_INTENT_SUCCEEDED)) {
            charge = StripeApiUtils.getCharge(paymentData.latestCharge(), requestOptions);
        }

        String paymentId = paymentData.paymentGatewayPaymentMetadata().get(PAYMENT_ID);
        if (paymentId == null) {
            throw new PaymentNotFoundException(PAYMENT_NOT_FOUND_ERROR_MESSAGE);
        }
        log.debug("Payment ID: {}", paymentId);
        Payment payment = paymentRepository.getPaymentById(UUID.fromString(paymentId));


        switch (eventType) {
            case PAYMENT_INTENT_SUCCEEDED:
                handleSuccessfulPayment(paymentData, payment, charge);
                break;
            case PAYMENT_INTENT_CANCELLED:
                handleCanceledPayment(paymentData, payment);
                break;
            case PAYMENT_INTENT_FAILED:
                handleFailedPayment(paymentData, payment);
                break;
            default:
                break;
        }
    }

    void handleSuccessfulPayment(PaymentData paymentData, Payment payment, Charge charge) {
        Map<String, String> metadata = payment.getPaymentGatewayMetadata();
        metadata.put(GATEWAY_PAYMENT_ID, paymentData.paymentGatewayPaymentId());
        metadata.put(GATEWAY_PAYMENT_METHOD_ID, paymentData.paymentGatewayPaymentMethodId());
        metadata.put(PAYMENT_METHOD_RECEIPT_URL, charge.getReceiptUrl());
        payment.setStatus(PaymentStatus.COMPLETED);
        Payment savedPayment = paymentRepository.save(payment);
        orderPort.completeOrder(savedPayment.getOrderId(), new CompleteOrder(savedPayment.getId()));
    }

    void handleCanceledPayment(PaymentData paymentData, Payment payment) {
        Map<String, String> metadata = payment.getPaymentGatewayMetadata();
        metadata.put(GATEWAY_PAYMENT_ID, paymentData.paymentGatewayPaymentId());
        metadata.put(PAYMENT_CANCELLATION_REASON, paymentData.cancellationReason());
        payment.setStatus(PaymentStatus.CANCELLED);
        Payment savedPayment = paymentRepository.save(payment);
        orderPort.cancelOrder(savedPayment.getOrderId(), new CancelOrder(savedPayment.getId()));
    }

    void handleFailedPayment(PaymentData paymentData, Payment payment) {
        Map<String, String> metadata = payment.getPaymentGatewayMetadata();
        metadata.put(GATEWAY_PAYMENT_ID, paymentData.paymentGatewayPaymentId());
        payment.setStatus(PaymentStatus.FAILED);
        Payment savedPayment = paymentRepository.save(payment);
        orderPort.failOrder(savedPayment.getOrderId(), new FailOrder(savedPayment.getId()));
    }

}

