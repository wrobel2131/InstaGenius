package com.instagenius.orderservice.domain;

import com.instagenius.orderservice.application.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderService implements OrderUseCase {
    private final OrderPersistencePort orderPersistencePort;
    private final PaymentPort paymentPort;
    private final ProductServiceClientFactory productServiceClientFactory;

    public OrderService(OrderPersistencePort orderPersistencePort, PaymentPort paymentPort, ProductServiceClientFactory productServiceClientFactory) {
        this.orderPersistencePort = orderPersistencePort;
        this.paymentPort = paymentPort;
        this.productServiceClientFactory = productServiceClientFactory;
    }

    @Override
    public CreatedOrder createOrder(List<OrderedProduct> orderedProducts, UUID userID) {
        System.out.println("Creating order");
        List<OrderItem> orderItems = new ArrayList<>();

        //TODO make this somehow async
        for (OrderedProduct orderedProduct : orderedProducts) {
            System.out.println("in for loop");
            ProductServiceClient productServiceClient = productServiceClientFactory.getClient(orderedProduct.type());
            Product product = productServiceClient.getProductById(orderedProduct.id());

            OrderItem orderItem = new OrderItem(null, product.id(), product.name(), product.description(),
                                                product.type(), orderedProduct.quantity(), product.price(), null, null);
            orderItems.add(orderItem);
        }

        Order newOrder = orderPersistencePort.save(new Order(userID, OrderStatus.PENDING, orderItems));

        CreatedPayment createdPayment = paymentPort.initializePaymentSession(new InitializePayment(newOrder.getId(), newOrder.getTotalPrice()));

        return new CreatedOrder(newOrder.getOrderId(), newOrder.getStatus(), createdPayment.paymentGatewaySessionId());
    }
}
