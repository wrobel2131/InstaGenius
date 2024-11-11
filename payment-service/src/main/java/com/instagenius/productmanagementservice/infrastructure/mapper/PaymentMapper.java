package com.instagenius.productmanagementservice.infrastructure.mapper;

import com.instagenius.productmanagementservice.domain.CreatedPayment;
import com.instagenius.productmanagementservice.infrastructure.dto.CreatedPaymentDto;
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
