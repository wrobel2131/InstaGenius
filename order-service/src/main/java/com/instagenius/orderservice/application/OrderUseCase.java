package com.instagenius.orderservice.application;

import com.instagenius.orderservice.domain.*;

import java.util.List;
import java.util.UUID;

public interface OrderUseCase {
    CreatedOrder createOrder(List<OrderedProduct> orderedProducts, UUID userId);
    Order findOrderByUserIdAndOrderId(UUID userId, String orderId);
    void completeOrder(UUID userId, UUID id, CompleteOrder completeOrder);
}
