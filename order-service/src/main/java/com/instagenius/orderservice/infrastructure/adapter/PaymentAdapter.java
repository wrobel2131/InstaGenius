package com.instagenius.orderservice.infrastructure.adapter;


import com.instagenius.orderservice.application.PaymentPort;
import com.instagenius.orderservice.domain.CreatedPayment;
import com.instagenius.orderservice.domain.InitializePayment;
import com.instagenius.orderservice.infrastructure.exception.FeignExceptionUtils;
import com.instagenius.orderservice.infrastructure.exception.PaymentException;
import com.instagenius.orderservice.infrastructure.mapper.PaymentMapper;
import com.instagenius.orderservice.infrastructure.rest.PaymentServiceClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentAdapter implements PaymentPort {
    private static final PaymentMapper paymentMapper = PaymentMapper.INSTANCE;
    private final PaymentServiceClient paymentServiceClient;

    @Override
    public CreatedPayment initializePaymentSession(InitializePayment initializePayment) {
        try {/* TODO mocked data*/
            return new CreatedPayment(UUID.fromString("c849c511-63cd-429a-bf56-f3a9de0a59e7"), "mockedSessionId");
//            return paymentMapper.toCreatedPayment(
//                    paymentServiceClient.initializePayment(
//                            paymentMapper.toInitializePaymentRequestDto(initializePayment)
//                    )
//            );
        } catch(FeignException e) {
            String errorMessage = FeignExceptionUtils.parseErrorResponse(e).message();
            throw new PaymentException(errorMessage, HttpStatus.valueOf(e.status()));
        }
    }
}
