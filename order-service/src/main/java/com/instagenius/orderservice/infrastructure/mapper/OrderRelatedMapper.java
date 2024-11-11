package com.instagenius.orderservice.infrastructure.mapper;

import com.instagenius.orderservice.domain.CompleteOrder;
import com.instagenius.orderservice.domain.CreatedOrder;
import com.instagenius.orderservice.domain.OrderedProduct;
import com.instagenius.orderservice.infrastructure.dto.CompleteOrderRequestDto;
import com.instagenius.orderservice.infrastructure.dto.CreatedOrderResponseDto;
import com.instagenius.orderservice.infrastructure.dto.OrderedProductRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderRelatedMapper {
    OrderRelatedMapper INSTANCE = Mappers.getMapper(OrderRelatedMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "type", target = "type")
    @Mapping(source = "quantity", target = "quantity")
    OrderedProduct toOrderedProduct(OrderedProductRequestDto orderedProductRequestDto);

    @Mapping(source = "orderId", target = "orderId")
    @Mapping(source = "orderStatus", target = "orderStatus")
    @Mapping(source = "paymentGatewaySessionId", target = "paymentGatewaySessionId")
    CreatedOrderResponseDto toCreatedOrderResponseDto(CreatedOrder createdOrder);

    @Mapping(source = "paymentId", target = "paymentId")
    CompleteOrder toCompleteOrder(CompleteOrderRequestDto completeOrderRequestDto);
}
