package com.instagenius.paymentservice.application;

import com.instagenius.paymentservice.domain.InitializedPayment;
import com.instagenius.paymentservice.domain.OrderedProduct;

import java.util.List;
import java.util.UUID;

public interface PaymentUseCase {
    InitializedPayment initializePayment(UUID userId, UUID orderId, String orderReferenceId,
                                         List<OrderedProduct> orderedProducts);

    void handlePaymentSuccess(String eventPayload, String signatureHeader);
}
