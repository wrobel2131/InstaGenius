package com.instagenius.orderservice.application;

import com.instagenius.orderservice.domain.ProductType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class OrderCompletionHandlerFactory {
    private final Map<ProductType, OrderCompletionHandler> handlers;

    public OrderCompletionHandlerFactory(List<OrderCompletionHandler> handlers) {
        this.handlers = handlers.stream().collect(Collectors.toMap(OrderCompletionHandler::getProductType, Function.identity()));
    }

    public OrderCompletionHandler getHandler(ProductType productType) {
        return handlers.get(productType);
    }
}
