package com.instagenius.paymentservice.infrastructure.mapper;

import com.instagenius.paymentservice.domain.Event;
import com.instagenius.paymentservice.infrastructure.adapter.EventEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EventMapper {
    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "eventId", target = "eventId")
    @Mapping(source = "type", target = "type")
    @Mapping(source = "livemode", target = "livemode")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "version", target = "version")
    Event toEvent(EventEntity eventEntity);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "eventId", target = "eventId")
    @Mapping(source = "type", target = "type")
    @Mapping(source = "livemode", target = "livemode")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "version", target = "version")
    EventEntity toEventEntity(Event event);
}
