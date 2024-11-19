package com.instagenius.paymentservice.infrastructure.adapter;

import com.instagenius.paymentservice.application.PaymentPersistencePort;
import com.instagenius.paymentservice.domain.Payment;
import com.instagenius.paymentservice.infrastructure.exception.PaymentNotFoundException;
import com.instagenius.paymentservice.infrastructure.mapper.PaymentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PaymentRepository implements PaymentPersistencePort {
    private final JpaPaymentRepository jpaPaymentRepository;
    private static final PaymentMapper paymentMapper = PaymentMapper.INSTANCE;

    @Override
    public Payment save(Payment payment) {
        return paymentMapper.toPayment(
          jpaPaymentRepository.save(
                  paymentMapper.toPaymentEntity(payment)
          )
        );
    }

    @Override
    public Payment getPaymentById(UUID id) {
        return paymentMapper.toPayment(
                jpaPaymentRepository.findPaymentEntityById(id).orElseThrow(() -> new PaymentNotFoundException("Payment not found!"))
        );
    }
}

@Repository
interface JpaPaymentRepository extends JpaRepository<PaymentEntity, UUID> {

    @Query(value = "SELECT p FROM PaymentEntity p WHERE p.id = :id")
    Optional<PaymentEntity> findPaymentEntityById(@Param("id") UUID id);
}