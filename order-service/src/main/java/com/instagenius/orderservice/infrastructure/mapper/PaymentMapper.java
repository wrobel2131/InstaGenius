package com.instagenius.orderservice.infrastructure.mapper;

import com.instagenius.orderservice.domain.CreatedPayment;
import com.instagenius.orderservice.domain.InitializePayment;
import com.instagenius.orderservice.infrastructure.dto.CreatedPaymentResponseDto;
import com.instagenius.orderservice.infrastructure.dto.InitializePaymentRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PaymentMapper {
    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);


    @Mapping(source = "paymentId", target = "paymentId")
    @Mapping(source = "paymentGatewaySessionId", target = "paymentGatewaySessionId")
    CreatedPayment toCreatedPayment(CreatedPaymentResponseDto createdPaymentResponseDto);


    @Mapping(source = "orderId", target = "orderId")
    @Mapping(source = "price.price", target = "price")
    @Mapping(source = "price.currency", target = "currency")
    InitializePaymentRequestDto toInitializePaymentRequestDto(InitializePayment initializePayment);
}
