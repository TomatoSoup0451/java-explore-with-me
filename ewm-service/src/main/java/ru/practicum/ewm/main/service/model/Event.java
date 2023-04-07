package ru.practicum.ewm.main.service.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewm.main.service.model.enums.EventState;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String annotation;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column
    private Date createdOn;

    @Column
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column
    private Date eventDate;

    @ManyToOne
    @JoinColumn(name = "initiator_id", nullable = false)
    private User initiator;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @Column
    private Boolean paid;

    @Column(columnDefinition = "default 0")
    private Integer participantLimit;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column
    private Date publishedOn;

    @Column
    private Boolean requestModeration;

    @Enumerated(javax.persistence.EnumType.STRING)
    private EventState state;

    @Column(nullable = false)
    private String title;

}
