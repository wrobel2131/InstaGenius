package com.instagenius.paymentservice.infrastructure.adapter;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Entity
@Table(name = "events")
public class EventEntity {

    @Column(name = "id", nullable = false, unique = true, updatable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "event_id", unique = true, nullable = false, updatable = false)
    private String eventId;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "livemode")
    private boolean livemode;

    @Column(name = "created_at")
    private Instant createdAt;

    @Version
    @Column(name = "version")
    private int version;
}
