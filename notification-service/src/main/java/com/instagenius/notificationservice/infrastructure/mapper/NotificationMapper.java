package com.instagenius.notificationservice.infrastructure.mapper;

import com.instagenius.notificationservice.domain.OrderNotification;
import com.instagenius.notificationservice.domain.PaymentNotification;
import com.instagenius.notificationservice.infrastructure.adapter.OrderNotificationEntity;
import com.instagenius.notificationservice.infrastructure.adapter.PaymentNotificationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NotificationMapper {
    NotificationMapper INSTANCE = Mappers.getMapper(NotificationMapper.class);


    OrderNotification toOrderNotification(OrderNotificationEntity orderNotificationEntity);

    OrderNotificationEntity toOrderNotificationEntity(OrderNotification orderNotification);

    PaymentNotification toPaymentNotification(PaymentNotificationEntity paymentNotificationEntity);

    PaymentNotificationEntity toPaymentNotificationEntity(PaymentNotification paymentNotification);
}
