package ru.practicum.ewm.main.service.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.practicum.ewm.main.service.model.enums.ParticipationStatus;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Accessors(chain = true)
@Table(name = "participation_requests")
public class ParticipationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Date created;

    @Enumerated(javax.persistence.EnumType.STRING)
    private ParticipationStatus status;

    @ManyToOne
    @JoinColumn(name = "requester_id")
    private User requester;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

}
