package com.instagenius.notificationservice.infrastructure.adapter;

import com.instagenius.notificationservice.application.PaymentNotificationPersistencePort;
import com.instagenius.notificationservice.domain.PaymentNotification;
import com.instagenius.notificationservice.infrastructure.mapper.NotificationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class PaymentNotificationRepository implements PaymentNotificationPersistencePort {
    private static final NotificationMapper notificationMapper = NotificationMapper.INSTANCE;
    private final JpaPaymentNotificationRepository jpaPaymentNotificationRepository;


    @Override
    public PaymentNotification save(PaymentNotification paymentNotification) {
        return notificationMapper.toPaymentNotification(
                jpaPaymentNotificationRepository.save(
                        notificationMapper.toPaymentNotificationEntity(paymentNotification)
                )
        );
    }
}

@Repository
interface JpaPaymentNotificationRepository extends JpaRepository<PaymentNotificationEntity, UUID> {

}
