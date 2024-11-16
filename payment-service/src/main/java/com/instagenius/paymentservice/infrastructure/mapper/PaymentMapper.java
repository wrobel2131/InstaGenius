package com.instagenius.paymentservice.infrastructure.mapper;

import com.instagenius.paymentservice.domain.InitializedPayment;
import com.instagenius.paymentservice.domain.Payment;
import com.instagenius.paymentservice.infrastructure.adapter.PaymentEntity;
import com.instagenius.paymentservice.infrastructure.dto.InitializedPaymentResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PaymentMapper {
    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "orderId", target = "orderId")
    @Mapping(source = "orderReferenceId", target = "orderReferenceId")
    @Mapping(source = "status", target = "status")
    @Mapping(target = "price", expression = "java(new Price(paymentEntity.getPrice(), paymentEntity.getCurrency()))")
    @Mapping(source = "paymentGatewayMetadata", target = "paymentGatewayMetadata")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt")
    @Mapping(source = "version", target = "version")
    Payment toPayment(PaymentEntity paymentEntity);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "orderId", target = "orderId")
    @Mapping(source = "orderReferenceId", target = "orderReferenceId")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "price.price", target = "price")
    @Mapping(source = "price.currency", target = "currency")
    @Mapping(source = "paymentGatewayMetadata", target = "paymentGatewayMetadata")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "updatedAt", target = "updatedAt", ignore = true)
    @Mapping(source = "version", target = "version")
    PaymentEntity toPaymentEntity(Payment payment);


    @Mapping(target = "paymentId", source = "paymentId")
    @Mapping(target = "paymentCheckoutSessionId", source = "paymentCheckoutSessionId")
    InitializedPaymentResponseDto toInitializedPaymentResponseDto(InitializedPayment initializedPayment);
}
