package com.instagenius.orderservice.infrastructure.mapper;

import com.instagenius.orderservice.domain.CreatedPayment;
import com.instagenius.orderservice.domain.InitializePayment;
import com.instagenius.orderservice.domain.ProductsToPay;
import com.instagenius.orderservice.infrastructure.dto.CreatedPaymentResponseDto;
import com.instagenius.orderservice.infrastructure.dto.InitializePaymentRequestDto;
import com.instagenius.orderservice.infrastructure.dto.ProductsToPayRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PaymentMapper {
    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);


    @Mapping(source = "paymentId", target = "paymentId")
    @Mapping(source = "paymentCheckoutSessionId", target = "paymentCheckoutSessionId")
    CreatedPayment toCreatedPayment(CreatedPaymentResponseDto createdPaymentResponseDto);

    @Mapping(source = "productId", target = "productId")
    ProductsToPayRequestDto toProductsToPayRequestDto(ProductsToPay productsToPay);


    List<ProductsToPayRequestDto> toProductsToPayRequestDtoList(List<ProductsToPay> productsToPayList);

    @Mapping(source = "orderId", target = "orderId")
    @Mapping(source = "orderedProducts", target = "orderedProducts")
    InitializePaymentRequestDto toInitializePaymentRequestDto(InitializePayment initializePayment);

}
