package com.instagenius.notificationservice.infrastructure.adapter;

import com.instagenius.notificationservice.application.OrderNotificationPersistencePort;
import com.instagenius.notificationservice.domain.OrderNotification;
import com.instagenius.notificationservice.infrastructure.mapper.NotificationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class OrderNotificationRepository implements OrderNotificationPersistencePort {
    private static final NotificationMapper notificationMapper = NotificationMapper.INSTANCE;
    private final JpaOrderNotificationRepository jpaOrderNotificationRepository;


    @Override
    public OrderNotification save(OrderNotification orderNotification) {
        return notificationMapper.toOrderNotification(
                jpaOrderNotificationRepository.save(
                        notificationMapper.toOrderNotificationEntity(orderNotification)
                )
        );
    }
}

@Repository
interface JpaOrderNotificationRepository extends JpaRepository<OrderNotificationEntity, UUID> {

}
