package com.instagenius.paymentservice.infrastructure.mapper;

import com.instagenius.paymentservice.domain.CancelOrder;
import com.instagenius.paymentservice.domain.CompleteOrder;
import com.instagenius.paymentservice.domain.FailOrder;
import com.instagenius.paymentservice.infrastructure.dto.CancelOrderRequestDto;
import com.instagenius.paymentservice.infrastructure.dto.CompleteOrderRequestDto;
import com.instagenius.paymentservice.infrastructure.dto.FailOrderRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderRelatedMapper {
    OrderRelatedMapper INSTANCE = Mappers.getMapper(OrderRelatedMapper.class);

    @Mapping(source = "paymentId", target = "paymentId")
    CompleteOrderRequestDto toCompleteOrderRequestDto(CompleteOrder completeOrder);

    CancelOrderRequestDto toCancelOrderRequestDto(CancelOrder cancelOrder);

    FailOrderRequestDto toFailOrderRequestDto(FailOrder failOrder);
}
