package com.instagenius.orderservice.domain;

import com.instagenius.orderservice.application.*;
import com.instagenius.orderservice.infrastructure.exception.ProductManagementException;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

public class OrderService implements OrderUseCase {
    private final OrderPersistencePort orderPersistencePort;
    private final ProductManagementPort productManagementPort;
    private final PaymentPort paymentPort;
    private final OrderCompletionHandlerFactory orderCompletionHandlerFactory;
    private final OrderEventProducerPort orderEventProducerPort;
    private final UserPort userPort;

    public OrderService(
            OrderPersistencePort orderPersistencePort, ProductManagementPort productManagementPort,
            PaymentPort paymentPort, OrderCompletionHandlerFactory orderCompletionHandlerFactory,
            OrderEventProducerPort orderEventProducerPort, UserPort userPort) {
        this.orderPersistencePort = orderPersistencePort;
        this.productManagementPort = productManagementPort;
        this.paymentPort = paymentPort;
        this.orderCompletionHandlerFactory = orderCompletionHandlerFactory;
        this.orderEventProducerPort = orderEventProducerPort;
        this.userPort = userPort;
    }

    @Transactional
    @Override
    public CreatedOrder createOrder(List<OrderedProduct> orderedProducts, UUID userID) {
        System.out.println("Creating order");

        /* Get list of ids from ordered products */
        List<UUID> productIds = orderedProducts.stream()
                                               .map(OrderedProduct::id)
                                               .toList();
        /* Get list of products from external service based on the ids */
        List<Product> products = productManagementPort.getProductsByIds(productIds);

        /* Create a map of products for easy access */
        Map<UUID, Product> productMap = products.stream()
                                                .collect(Collectors.toMap(Product::id, Function.identity()));

        /* Create order items from ordered products */
        List<OrderItem> orderItems = orderedProducts.stream()
                                                    .map(orderedProduct -> {
                                                        Product product = productMap.get(orderedProduct.id());

                                                        if (product == null) {
                                                            throw new ProductManagementException("Product not found!",
                                                                                                 HttpStatus.NOT_FOUND);
                                                        }

                                                        return new OrderItem(
                                                                null,
                                                                product.id(),
                                                                product.name(),
                                                                product.description(),
                                                                product.type(),
                                                                orderedProduct.quantity(),
                                                                product.price(),
                                                                null,
                                                                product.attributes()
                                                        );
                                                    })
                                                    .toList();

        Order newOrder = orderPersistencePort.save(new Order(userID, OrderStatus.PENDING, orderItems, null));

        CreatedPayment createdPayment = paymentPort.initializePaymentSession(
                new InitializePayment(newOrder.getId(), newOrder.getReferenceId(),
                                      newOrder
                                              .getItems()
                                              .stream()
                                              .map(i -> new ProductsToPay(i.getProduct().id(), i.getQuantity()))
                                              .toList())
        );

        /* Get user information from user service */
        UserProfile userProfile = userPort.getUserProfile(newOrder.getUserId());
        String paymentInstructions = "SOME Instructions for user"; //TODO prepare some instructions for user (maybe with stripe link or something

        OrderEvent orderEvent = new OrderEvent(
                newOrder.getReferenceId(),
                userProfile.email(),
                userProfile.firstName(),
                userProfile.lastName(),
                newOrder.getStatus(),
                newOrder.getItems().stream().map(OrderItem::getProduct).toList(),
                newOrder.getTotalPrice(),
                newOrder.getCreatedAt(),
                paymentInstructions
                );

        /* Publish an event about creation of the order for consumers */
        orderEventProducerPort.publishOrderEvent(orderEvent);

        return new CreatedOrder(newOrder.getReferenceId(), newOrder.getStatus(),
                                createdPayment.paymentCheckoutSessionId());
    }

    @Override
    public Order findOrderByUserIdAndReferenceId(UUID userId, String referenceId) {
        System.out.println("Finding order");
        return orderPersistencePort.findOrderByUserIdAndReferenceId(userId, referenceId);
    }

    @Transactional
    @Override
    public void completeOrder(UUID id, CompleteOrder completeOrder) {
        System.out.println("Completing order");
        Order order = orderPersistencePort.findOrderById(id);
        order.setStatus(OrderStatus.COMPLETED);
        order.setPaymentId(completeOrder.paymentId());

        Order completedOrder = orderPersistencePort.save(order);
        UUID userId = completedOrder.getUserId();


        /* Perform some action to complete the order */
        completedOrder.getItems().forEach(o -> {
            System.out.println("Handling order item");
            OrderCompletionHandler orderCompletionHandler =
                    orderCompletionHandlerFactory.getHandler(o.getProduct().type());
            orderCompletionHandler.handleOrderItemCompletion(userId, o);
        });

        /* Get user information from user service */
        UserProfile userProfile = userPort.getUserProfile(completedOrder.getUserId());
        String paymentInstructions = "SOME Instructions for user"; //TODO prepare some instructions for user (maybe with stripe link or something

        OrderEvent orderEvent = new OrderEvent(
                completedOrder.getReferenceId(),
                userProfile.email(),
                userProfile.firstName(),
                userProfile.lastName(),
                completedOrder.getStatus(),
                completedOrder.getItems().stream().map(OrderItem::getProduct).toList(),
                completedOrder.getTotalPrice(),
                completedOrder.getCreatedAt(),
                paymentInstructions
        );

        /* Publish an event about creation of the order for consumers */
        orderEventProducerPort.publishOrderEvent(orderEvent);
        System.out.println("Order completed");
    }

    @Transactional
    @Override
    public void cancelOrder(UUID id, CancelOrder cancelOrder) {
        System.out.println("Cancelling order");
        Order order = orderPersistencePort.findOrderById(id);
        order.setStatus(OrderStatus.CANCELED);
        order.setPaymentId(cancelOrder.paymentId());
        Order canceledOrder = orderPersistencePort.save(order);

        /* Get user information from user service */
        UserProfile userProfile = userPort.getUserProfile(canceledOrder.getUserId());
        String paymentInstructions = "SOME Instructions for user"; //TODO prepare some instructions for user (maybe with stripe link or something

        OrderEvent orderEvent = new OrderEvent(
                canceledOrder.getReferenceId(),
                userProfile.email(),
                userProfile.firstName(),
                userProfile.lastName(),
                canceledOrder.getStatus(),
                canceledOrder.getItems().stream().map(OrderItem::getProduct).toList(),
                canceledOrder.getTotalPrice(),
                canceledOrder.getCreatedAt(),
                paymentInstructions
        );

        /* Publish an event about creation of the order for consumers */
        orderEventProducerPort.publishOrderEvent(orderEvent);
        System.out.println("Canceled order");
    }

    @Transactional
    @Override
    public void failOrder(UUID id, FailOrder failOrder) {
        System.out.println("Failing order");
        Order order = orderPersistencePort.findOrderById(id);
        order.setStatus(OrderStatus.FAILED);
        order.setPaymentId(failOrder.paymentId());
        Order failedOrder = orderPersistencePort.save(order);

        /* Get user information from user service */
        UserProfile userProfile = userPort.getUserProfile(failedOrder.getUserId());
        String paymentInstructions = "SOME Instructions for user"; //TODO prepare some instructions for user (maybe with stripe link or something

        OrderEvent orderEvent = new OrderEvent(
                failedOrder.getReferenceId(),
                userProfile.email(),
                userProfile.firstName(),
                userProfile.lastName(),
                failedOrder.getStatus(),
                failedOrder.getItems().stream().map(OrderItem::getProduct).toList(),
                failedOrder.getTotalPrice(),
                failedOrder.getCreatedAt(),
                paymentInstructions
        );

        /* Publish an event about creation of the order for consumers */
        orderEventProducerPort.publishOrderEvent(orderEvent);
        System.out.println("Canceled order");
    }
}
