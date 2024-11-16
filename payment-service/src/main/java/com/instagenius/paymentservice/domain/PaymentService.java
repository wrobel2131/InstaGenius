package com.instagenius.paymentservice.domain;

import com.instagenius.paymentservice.application.*;
import com.instagenius.paymentservice.infrastructure.exception.PaymentGatewayException;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class PaymentService implements PaymentUseCase {
    private final PaymentPersistencePort paymentPersistencePort;
    private final PaymentGatewayPort paymentGatewayPort;
    private final ProductManagementPort productManagementPort;
    private final AsyncPaymentProcessPort asyncPaymentProcessPort;

    public PaymentService(PaymentPersistencePort paymentPersistencePort, PaymentGatewayPort paymentGatewayPort,
                          ProductManagementPort productManagementPort, AsyncPaymentProcessPort asyncPaymentProcessPort) {
        this.paymentPersistencePort = paymentPersistencePort;
        this.paymentGatewayPort = paymentGatewayPort;
        this.productManagementPort = productManagementPort;
        this.asyncPaymentProcessPort = asyncPaymentProcessPort;
    }


    @Override
    public InitializedPayment initializePayment(UUID userId, UUID orderId, String orderReferenceId, List<OrderedProduct> orderedProducts) {
        // Get products by ids from product management service
        List<Product> products = productManagementPort.getProductsByIds(orderedProducts.stream().map(OrderedProduct::productId).toList());
        System.out.println("PaymentService.initializePayment: products = " + products);

        // Create a map of products and their quantities
        Map<Product, Integer> productQuantityMap = orderedProducts
                .stream()
                .collect(Collectors.toMap(
                        orderedItem -> products
                                .stream()
                                .filter(p -> p.id().equals(orderedItem.productId()))
                                .findFirst()
                                .orElseThrow(() -> new PaymentGatewayException("Error while creating payment checkout session!", HttpStatus.INTERNAL_SERVER_ERROR)),
                        OrderedProduct::quantity
                ));
        System.out.println(
                "PaymentService.initializePayment: productQuantityMap = " + productQuantityMap
        );

        // Create a payment session
        Payment initializedPayment = paymentGatewayPort.createPaymentSession( new Payment(userId, orderId, orderReferenceId, PaymentStatus.PENDING, null, new HashMap<>(), null, null, 0), productQuantityMap);

        // Save the payment in the database
        Payment createdPayment = paymentPersistencePort.save(initializedPayment);
        System.out.println("PaymentService.initializePayment: createdPayment = " + createdPayment);
        return new InitializedPayment(createdPayment.getId(), createdPayment.getPaymentGatewayMetadata().get("paymentGatewayCheckoutSessionId"));
    }

    @Override
    public void handlePaymentSuccess(String eventPayload, String signatureHeader) {
        PaymentData paymentData = paymentGatewayPort.getPaymentDataFromEvent(eventPayload, signatureHeader);

        asyncPaymentProcessPort.processPaymentData(paymentData);
    }


}
