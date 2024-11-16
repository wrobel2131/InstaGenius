package com.instagenius.paymentservice.infrastructure.mapper;

import com.instagenius.paymentservice.domain.CompleteOrder;
import com.instagenius.paymentservice.infrastructure.dto.CompleteOrderRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderRelatedMapper {
    OrderRelatedMapper INSTANCE = Mappers.getMapper(OrderRelatedMapper.class);

    @Mapping(source = "paymentId", target = "paymentId")
    CompleteOrderRequestDto toCompleteOrderRequestDto(CompleteOrder completeOrder);
}
