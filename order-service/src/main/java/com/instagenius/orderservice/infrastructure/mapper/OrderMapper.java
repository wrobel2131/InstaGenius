package com.instagenius.orderservice.infrastructure.mapper;

import com.instagenius.orderservice.domain.Order;
import com.instagenius.orderservice.infrastructure.adapter.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    Order toOrder(OrderEntity entity);

    OrderEntity toOrderEntity(Order order);
}
