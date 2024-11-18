package com.instagenius.orderservice.infrastructure.mapper;

import com.instagenius.orderservice.domain.*;
import com.instagenius.orderservice.infrastructure.dto.*;
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

    @Mapping(source = "referenceId", target = "referenceId")
    @Mapping(source = "orderStatus", target = "orderStatus")
    @Mapping(source = "paymentGatewaySessionId", target = "paymentGatewaySessionId")
    CreatedOrderResponseDto toCreatedOrderResponseDto(CreatedOrder createdOrder);

    @Mapping(source = "paymentId", target = "paymentId")
    CompleteOrder toCompleteOrder(CompleteOrderRequestDto completeOrderRequestDto);

    @Mapping(source = "paymentId", target = "paymentId")
    CancelOrder toCancelOrder(CancelOrderRequestDto cancelOrderRequestDto);

    @Mapping(source = "paymentId", target = "paymentId")
    FailOrder toFailOrder(FailOrderRequestDto failOrderRequestDto);
}
