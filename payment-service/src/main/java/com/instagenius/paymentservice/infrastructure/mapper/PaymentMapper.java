package com.instagenius.paymentservice.infrastructure.mapper;

import com.instagenius.paymentservice.domain.CreatedPayment;
import com.instagenius.paymentservice.infrastructure.dto.CreatedPaymentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PaymentMapper {
    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);

    @Mapping(source = "paymentId", target = "paymentId")
    @Mapping(source = "paymentGatewaySessionId", target = "paymentGatewaySessionId")
    CreatedPaymentDto toCreatedPaymentDto(CreatedPayment createdPayment);
}
