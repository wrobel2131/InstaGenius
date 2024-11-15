package com.instagenius.paymentservice.infrastructure.mapper;

import com.instagenius.paymentservice.domain.InitializedPayment;
import com.instagenius.paymentservice.infrastructure.dto.InitializedPaymentResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PaymentMapper {
    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);


    @Mapping(target = "paymentId", source = "paymentId")
    @Mapping(target = "checkoutSessionId", source = "checkoutSessionId")
    InitializedPaymentResponseDto toInitializedPaymentResponseDto(InitializedPayment initializedPayment);
}
