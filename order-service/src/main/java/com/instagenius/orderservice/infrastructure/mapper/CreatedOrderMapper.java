package com.instagenius.orderservice.infrastructure.mapper;

import com.instagenius.orderservice.domain.CreatedOrder;
import com.instagenius.orderservice.domain.OrderedProduct;
import com.instagenius.orderservice.infrastructure.dto.CreatedOrderResponseDto;
import com.instagenius.orderservice.infrastructure.dto.OrderedProductRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CreatedOrderMapper {
    CreatedOrderMapper INSTANCE = Mappers.getMapper(CreatedOrderMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "type", target = "type")
    @Mapping(source = "quantity", target = "quantity")
    OrderedProduct toOrderedProduct(OrderedProductRequestDto orderedProductRequestDto);

    @Mapping(source = "orderId", target = "orderId")
    @Mapping(source = "orderStatus", target = "orderStatus")
    @Mapping(source = "paymentGatewaySessionId", target = "paymentGatewaySessionId")
    CreatedOrderResponseDto toCreatedOrderResponseDto(CreatedOrder createdOrder);
}
