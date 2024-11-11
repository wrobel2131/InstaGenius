package com.instagenius.orderservice.infrastructure.mapper;

import com.instagenius.orderservice.domain.*;
import com.instagenius.orderservice.infrastructure.adapter.OrderEntity;
import com.instagenius.orderservice.infrastructure.adapter.OrderItemEntity;
import com.instagenius.orderservice.infrastructure.dto.OrderItemDto;
import com.instagenius.orderservice.infrastructure.dto.OrderResponseDto;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@UtilityClass
public class OrderMapper {
    public Order toOrder(OrderEntity orderEntity) {
        UUID id = orderEntity.getId();
        String orderId = orderEntity.getOrderId();
        UUID userId = orderEntity.getUserId();
        OrderStatus status = orderEntity.getStatus();
        Instant createdAt = orderEntity.getCreatedAt();
        Instant updatedAt = orderEntity.getUpdatedAt();
        List<OrderItem> items = orderEntity
                .getItems()
                .stream()
                .map(OrderMapper::toOrderItem)
                .toList();
        int version = orderEntity.getVersion();
        return new Order(id, orderId, userId, status, items, createdAt, updatedAt, version, orderEntity.getPaymentId());
    }

    public OrderEntity toOrderEntity(Order order) {
        UUID id = order.getId();
        String orderId = order.getOrderId();
        UUID userId = order.getUserId();
        OrderStatus status = order.getStatus();
        BigDecimal totalPrice = order.getTotalPrice().price();
        String currency = order.getTotalPrice().currency();
        Instant createdAt = order.getCreatedAt();
        Instant updatedAt = order.getUpdatedAt();
        int version = order.getVersion();
        UUID paymentId = order.getPaymentId();

        OrderEntity orderEntity = OrderEntity
                .builder()
                .id(id)
                .orderId(orderId)
                .userId(userId)
                .status(status)
                .totalPrice(totalPrice)
                .currency(currency)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .version(version)
                .paymentId(paymentId)
                .build();
        List<OrderItemEntity> items = order
                .getItems()
                .stream()
                .map(i -> toOrderItemEntity(i, orderEntity))
                .toList();
        orderEntity.setItems(items);
        return orderEntity;

    }

    public OrderResponseDto toOrderResponseDto(Order order) {
        String orderId = order.getOrderId();
        Instant createdAt = order.getCreatedAt();
        String status = order.getStatus().name();
        BigDecimal totalPrice = order.getTotalPrice().price();
        String currency = order.getTotalPrice().currency();
        List<OrderItemDto> items = order
                .getItems()
                .stream()
                .map(OrderMapper::toOrderItemDto)
                .toList();
        return new OrderResponseDto(orderId, createdAt, status, totalPrice, currency, items);
    }

    private OrderItemDto toOrderItemDto(OrderItem orderItem) {
        String productName = orderItem.getProduct().name();
        String productDescription = orderItem.getProduct().description();
        int quantity = orderItem.getQuantity();
        BigDecimal unitPrice = orderItem.getUnitPrice().price();
        BigDecimal totalPrice = orderItem.getTotalPrice().price();
        String currency = orderItem.getTotalPrice().currency();

        Map<String, Object> attributes = orderItem.getProduct().attributes();
        return new OrderItemDto(productName, productDescription, quantity, unitPrice, totalPrice, currency, attributes);
    }



    private OrderItemEntity toOrderItemEntity(OrderItem orderItem, OrderEntity orderEntity) {
        UUID id = orderItem.getId();
        UUID productId = orderItem.getProduct().id();
        String productName = orderItem.getProduct().name();
        String productDescription = orderItem.getProduct().description();
        ProductType productType = orderItem.getProduct().type();
        BigDecimal unitPrice = orderItem.getUnitPrice().price();
        BigDecimal totalPrice = orderItem.getTotalPrice().price();
        Instant createdAt = orderItem.getCreatedAt();
        int quantity = orderItem.getQuantity();
        String currency = orderItem.getTotalPrice().currency();
        int version = orderItem.getVersion();
        Map<String, Object> attributes = orderItem.getProduct().attributes();

        return new OrderItemEntity(id, productId, productName, productDescription, productType, unitPrice, totalPrice
                , currency, quantity, createdAt, version, attributes, orderEntity);
    }

    private OrderItem toOrderItem(OrderItemEntity orderItemEntity) {
        UUID id = orderItemEntity.getId();
        Product product = new Product(
                orderItemEntity.getProductId(), orderItemEntity.getProductName(),
                orderItemEntity.getProductDescription(), orderItemEntity.getProductType(),
                new Price(orderItemEntity.getUnitPrice(), orderItemEntity.getCurrency()), orderItemEntity.getAttributes());
        int quantity = orderItemEntity.getQuantity();
        Price unitPrice = new Price(orderItemEntity.getUnitPrice(), orderItemEntity.getCurrency());
        Price totalPrice = new Price(orderItemEntity.getTotalPrice(), orderItemEntity.getCurrency());
        Instant createdAt = orderItemEntity.getCreatedAt();
        int version = orderItemEntity.getVersion();
        return new OrderItem(id, product, quantity, unitPrice, totalPrice, createdAt, version);
    }

}
