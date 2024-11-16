package com.instagenius.paymentservice.infrastructure.adapter;

import com.instagenius.paymentservice.application.OrderPort;
import com.instagenius.paymentservice.domain.CompleteOrder;
import com.instagenius.paymentservice.infrastructure.dto.ProductIdsRequestDto;
import com.instagenius.paymentservice.infrastructure.exception.FeignExceptionUtils;
import com.instagenius.paymentservice.infrastructure.exception.OrderException;
import com.instagenius.paymentservice.infrastructure.exception.ProductManagementException;
import com.instagenius.paymentservice.infrastructure.mapper.OrderRelatedMapper;
import com.instagenius.paymentservice.infrastructure.rest.OrderClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderAdapter implements OrderPort {
    private final OrderClient orderClient;
    private static final OrderRelatedMapper orderRelatedMapper = OrderRelatedMapper.INSTANCE;

    @Override
    public void completeOrder(UUID orderId, CompleteOrder completeOrder) {
        try {
            orderClient.completeOrder(orderId, orderRelatedMapper.toCompleteOrderRequestDto(completeOrder));
        } catch(FeignException e) {
            String errorMessage = FeignExceptionUtils.parseErrorResponse(e).message();

            throw new OrderException(errorMessage, HttpStatus.valueOf(e.status()));
        }
    }
}
