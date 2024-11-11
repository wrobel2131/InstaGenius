package com.instagenius.orderservice.domain;

import com.instagenius.orderservice.application.*;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderService implements OrderUseCase {
    private final OrderPersistencePort orderPersistencePort;
    private final PaymentPort paymentPort;
    private final ProductServiceClientFactory productServiceClientFactory;
    private final OrderCompletionHandlerFactory orderCompletionHandlerFactory;

    public OrderService(OrderPersistencePort orderPersistencePort, PaymentPort paymentPort, ProductServiceClientFactory productServiceClientFactory, OrderCompletionHandlerFactory orderCompletionHandlerFactory) {
        this.orderPersistencePort = orderPersistencePort;
        this.paymentPort = paymentPort;
        this.productServiceClientFactory = productServiceClientFactory;
        this.orderCompletionHandlerFactory = orderCompletionHandlerFactory;
    }

    @Transactional
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
                                                product.type(), orderedProduct.quantity(), product.price(), null, product.attributes());
            orderItems.add(orderItem);
        }

        Order newOrder = orderPersistencePort.save(new Order(userID, OrderStatus.PENDING, orderItems, null));

        CreatedPayment createdPayment = paymentPort.initializePaymentSession(new InitializePayment(newOrder.getId(), newOrder.getTotalPrice()));

        return new CreatedOrder(newOrder.getOrderId(), newOrder.getStatus(), createdPayment.paymentGatewaySessionId());
    }

    @Override
    public Order findOrderByUserIdAndOrderId(UUID userId, String orderId) {
        System.out.println("Finding order");
        return orderPersistencePort.findOrderByUserIdAndOrderId(userId, orderId);
    }

    @Transactional
    @Override
    public void completeOrder(UUID userId, UUID id, CompleteOrder completeOrder) {
        System.out.println("Completing order");
        Order order = orderPersistencePort.findOrderByUserIdAndId(userId, id);
        order.setStatus(OrderStatus.COMPLETED);
        order.setPaymentId(completeOrder.paymentId());

        Order completedOrder = orderPersistencePort.save(order);


        /* Perform some action to complete the order */
        completedOrder.getItems().forEach(o -> {
            System.out.println("Handling order item");
            OrderCompletionHandler orderCompletionHandler =
                    orderCompletionHandlerFactory.getHandler(o.getProduct().type());
            orderCompletionHandler.handleOrderItemCompletion(o);
        });
        System.out.println("Order completed");

    }

}
