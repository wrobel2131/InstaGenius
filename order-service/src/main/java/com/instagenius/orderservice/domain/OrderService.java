package com.instagenius.orderservice.domain;

import com.instagenius.orderservice.application.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderService implements OrderUseCase {
    private final OrderPersistencePort orderPersistencePort;
    private final ProductServiceClientFactory productServiceClientFactory;

    public OrderService(OrderPersistencePort orderPersistencePort, ProductServiceClientFactory productServiceClientFactory) {
        this.orderPersistencePort = orderPersistencePort;
        this.productServiceClientFactory = productServiceClientFactory;
    }

    @Override
    public Order createOrder(List<OrderedProduct> orderedProducts, UUID userID) {
        List<OrderItem> orderItems = new ArrayList<>();

        //TODO make this somehow async
        for (OrderedProduct orderedProduct : orderedProducts) {
            ProductServiceClient productServiceClient = productServiceClientFactory.getClient(orderedProduct.productType());
            Product product = productServiceClient.getProductById(orderedProduct.id());

            OrderItem orderItem = new OrderItem(null, product.id(), product.name(), product.description(),
                                                product.type(), orderedProduct.quantity(), product.price(),
                                                product.details(), null);
            orderItems.add(orderItem);
        }

        Order newOrder = orderPersistencePort.save(new Order(userID, OrderStatus.PENDING, orderItems));

        //TODO
        // call to payment service which will return checkout session id from stripe and then return orderId and
        // checkout id


    }
}
