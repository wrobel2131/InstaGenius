package com.instagenius.orderservice.application;

import com.instagenius.orderservice.domain.*;

import java.util.List;
import java.util.UUID;

public interface OrderUseCase {
    CreatedOrder createOrder(List<OrderedProduct> orderedProducts, UUID userId);
    Order findOrderByUserIdAndReferenceId(UUID userId, String referenceId);
    void completeOrder(UUID id, CompleteOrder completeOrder);
    void cancelOrder(UUID id, CancelOrder cancelOrder);
    void failOrder(UUID id, FailOrder failOrder);
}
