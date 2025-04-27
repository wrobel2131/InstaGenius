package com.instagenius.paymentservice.infrastructure.adapter;

import com.instagenius.paymentservice.application.OrderPort;
import com.instagenius.paymentservice.domain.CancelOrder;
import com.instagenius.paymentservice.domain.CompleteOrder;
import com.instagenius.paymentservice.domain.FailOrder;
import com.instagenius.paymentservice.infrastructure.exception.FeignExceptionUtils;
import com.instagenius.paymentservice.infrastructure.exception.OrderException;
import com.instagenius.paymentservice.infrastructure.mapper.OrderRelatedMapper;
import com.instagenius.paymentservice.infrastructure.rest.OrderClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderAdapter implements OrderPort {
    private final OrderClient orderClient;
    private static final OrderRelatedMapper orderRelatedMapper = OrderRelatedMapper.INSTANCE;

    @Override
    public void completeOrder(UUID orderId, CompleteOrder completeOrder) {
        try {
            log.debug("Completing order...");
            orderClient.completeOrder(orderId, orderRelatedMapper.toCompleteOrderRequestDto(completeOrder));
        } catch(FeignException e) {
            String errorMessage = FeignExceptionUtils.parseErrorResponse(e).message();

            throw new OrderException(errorMessage, HttpStatus.valueOf(e.status()));
        }
    }

    @Override
    public void cancelOrder(UUID orderId, CancelOrder cancelOrder) {
        try {
            log.debug("Cancelling order...");
            orderClient.cancelOrder(orderId, orderRelatedMapper.toCancelOrderRequestDto(cancelOrder));
        } catch(FeignException e) {
            String errorMessage = FeignExceptionUtils.parseErrorResponse(e).message();

            throw new OrderException(errorMessage, HttpStatus.valueOf(e.status()));
        }
    }

    @Override
    public void failOrder(UUID orderId, FailOrder failOrder) {
        try {
            log.debug("Failing order...");
            orderClient.failOrder(orderId, orderRelatedMapper.toFailOrderRequestDto(failOrder));
        } catch(FeignException e) {
            String errorMessage = FeignExceptionUtils.parseErrorResponse(e).message();

            throw new OrderException(errorMessage, HttpStatus.valueOf(e.status()));
        }
    }
}
