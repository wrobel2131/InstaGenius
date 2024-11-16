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


@Service
@RequiredArgsConstructor
public class PaymentAdapter implements PaymentPort {
    private static final PaymentMapper paymentMapper = PaymentMapper.INSTANCE;
    private final PaymentServiceClient paymentServiceClient;

    @Override
    public CreatedPayment initializePaymentSession(InitializePayment initializePayment) {
        try {
            return paymentMapper.toCreatedPayment(
                    paymentServiceClient.initializePayment(
                            paymentMapper.toInitializePaymentRequestDto(
                                    initializePayment
            )));

        } catch(FeignException e) {
            String errorMessage = FeignExceptionUtils.parseErrorResponse(e).message();
            throw new PaymentException(errorMessage, HttpStatus.valueOf(e.status()));
        }
    }
}
