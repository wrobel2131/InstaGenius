package com.instagenius.paymentservice.infrastructure.adapter;

import com.instagenius.paymentservice.application.EventPersistencePort;
import com.instagenius.paymentservice.domain.Event;
import com.instagenius.paymentservice.infrastructure.mapper.EventMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class EventRepository implements EventPersistencePort {
    private final JpaEventRepository jpaEventRepository;
    private static final EventMapper eventMapper = EventMapper.INSTANCE;

    @Override
    public Event saveEvent(Event event) {
        return eventMapper.toEvent(
                jpaEventRepository.save(
                        eventMapper.toEventEntity(event)
                )
        );
    }

    @Override
    public boolean existsByEventId(String eventId) {
        return jpaEventRepository.existsByEventId(eventId);
    }

}

@Repository
interface JpaEventRepository extends JpaRepository<EventEntity, UUID> {

    @Query(value = "SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM EventEntity e WHERE e.eventId = :eventId")
    boolean existsByEventId(@Param("eventId") String eventId);
}

